package com.works.metrostation.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import javax.persistence.MappedSuperclass;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorObject {

    protected int statusCode;
    protected Date timestamp;
    protected String message;
    protected String description;
}

@Getter
class ApiError extends ErrorObject{

    private List<String> errors;

    public ApiError(int statusCode, Date timestamp, String message, String description, List<String> errors) {
        super(statusCode,timestamp,message,description);
        this.errors = errors;
    }
}
