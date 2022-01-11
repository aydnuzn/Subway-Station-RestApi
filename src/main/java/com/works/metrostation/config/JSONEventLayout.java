package com.works.metrostation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import net.minidev.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

public class JSONEventLayout extends Layout {

    private boolean locationInfo = false;
    private String customUserFields;

    private boolean ignoreThrowable = false;

    private boolean activeIgnoreThrowable = ignoreThrowable;
    private String hostName;
    private String threadName;
    private long timestamp;
    private String ndc;
    private Map mdc;
    private LocationInfo info;
    private HashMap<String, Object> exceptionInformation;
    private static Integer version = 1;

    private JSONObject logstashEvent;
    private Map<String,Object> logstashEventMap;

    public static final TimeZone UTC = TimeZone.getTimeZone("UTC");
    public static final FastDateFormat ISO_DATETIME_TIME_ZONE_FORMAT_WITH_MILLIS = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", UTC);
    public static final String ADDITIONAL_DATA_PROPERTY = "com.works.metrostation.config.JSONEventLayout.UserFields";

    public static String dateFormat(long timestamp) {
        return ISO_DATETIME_TIME_ZONE_FORMAT_WITH_MILLIS.format(timestamp);
    }

    /**
     * For backwards compatibility, the default is to generate location information
     * in the log messages.
     */
    public JSONEventLayout() {
        this(true);
    }

    /**
     * Creates a layout that optionally inserts location information into log messages.
     *
     * @param locationInfo whether or not to include location information in the log messages.
     */
    public JSONEventLayout(boolean locationInfo) {
        this.locationInfo = locationInfo;

        try {
            this.hostName = java.net.InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            this.hostName = "unknown-host";
        }
    }

    @SneakyThrows
    public String format(LoggingEvent loggingEvent) {
        threadName = loggingEvent.getThreadName();
        timestamp = loggingEvent.getTimeStamp();
        exceptionInformation = new HashMap<String, Object>();
        mdc = loggingEvent.getProperties();
        ndc = loggingEvent.getNDC();

    //    logstashEvent = new JSONObject();
        logstashEventMap = new LinkedHashMap<>();

        String whoami = this.getClass().getSimpleName();

        /**
         * All v1 of the event format requires is
         * "@timestamp" and "@version"
         * Every other field is arbitrary
         */
        addEventData("@version", version);
        addEventData("@timestamp", dateFormat(timestamp));

        addEventData("level", loggingEvent.getLevel().toString());
        logstashEventMap.put("message", loggingEvent.getRenderedMessage());

        if (locationInfo) {
            info = loggingEvent.getLocationInformation();
            addEventData("file", info.getFileName());
            addEventData("line_number", info.getLineNumber());
            addEventData("class", info.getClassName());
            addEventData("method", info.getMethodName());
        }

        addEventData("url_address", FilterConfig.URL_ADDRESS);
        addEventData("ip_address", FilterConfig.IP_ADDRESS);
        addEventData("session_id", FilterConfig.SESSION_ID);
        addEventData("user_agent", FilterConfig.USER_AGENT);

        /**
         * Extract and add fields from log4j config, if defined
         */
        if (getUserFields() != null) {
            String userFlds = getUserFields();
            LogLog.debug("["+whoami+"] Got user data from log4j property: "+ userFlds);
            addUserFields(userFlds);
        }

        /**
         * Extract fields from system properties, if defined
         * Note that CLI props will override conflicts with log4j config
         */
        if (System.getProperty(ADDITIONAL_DATA_PROPERTY) != null) {
            if (getUserFields() != null) {
                LogLog.warn("["+whoami+"] Loading UserFields from command-line. This will override any UserFields set in the log4j configuration file");
            }
            String userFieldsProperty = System.getProperty(ADDITIONAL_DATA_PROPERTY);
            LogLog.debug("["+whoami+"] Got user data from system property: " + userFieldsProperty);
            addUserFields(userFieldsProperty);
        }

        /**
         * Now we start injecting our own stuff.
         */
        logstashEventMap.put("source_host", hostName);

        if (loggingEvent.getThrowableInformation() != null) {
            final ThrowableInformation throwableInformation = loggingEvent.getThrowableInformation();
            if (throwableInformation.getThrowable().getClass().getCanonicalName() != null) {
                exceptionInformation.put("exception_class", throwableInformation.getThrowable().getClass().getCanonicalName());
            }
            if (throwableInformation.getThrowable().getMessage() != null) {
                exceptionInformation.put("exception_message", throwableInformation.getThrowable().getMessage());
            }
            if (throwableInformation.getThrowableStrRep() != null) {
                String stackTrace = StringUtils.join(throwableInformation.getThrowableStrRep(), "\n");
                exceptionInformation.put("stacktrace", stackTrace);
            }
            addEventData("exception", exceptionInformation);
        }

        addEventData("logger_name", loggingEvent.getLoggerName());
        addEventData("mdc", mdc);
        addEventData("ndc", ndc);
        addEventData("thread_name", threadName);

        String resultJSON = new ObjectMapper().writeValueAsString(logstashEventMap);
        return resultJSON + "\n";
        //return logstashEvent.toString() + "\n";
    }

    public boolean ignoresThrowable() {
        return ignoreThrowable;
    }

    /**
     * Query whether log messages include location information.
     *
     * @return true if location information is included in log messages, false otherwise.
     */
    public boolean getLocationInfo() {
        return locationInfo;
    }

    /**
     * Set whether log messages should include location information.
     *
     * @param locationInfo true if location information should be included, false otherwise.
     */
    public void setLocationInfo(boolean locationInfo) {
        this.locationInfo = locationInfo;
    }

    public String getUserFields() { return customUserFields; }
    public void setUserFields(String userFields) { this.customUserFields = userFields; }

    public void activateOptions() {
        activeIgnoreThrowable = ignoreThrowable;
    }

    private void addUserFields(String data) {
        if (null != data) {
            String[] pairs = data.split(",");
            for (String pair : pairs) {
                String[] userField = pair.split(":", 2);
                if (userField[0] != null) {
                    String key = userField[0];
                    String val = userField[1];
                    addEventData(key, val);
                }
            }
        }
    }

    private void addEventData(String keyname, Object keyval) {
        if (null != keyval) {
            logstashEventMap.put(keyname, keyval);
        }
    }


}