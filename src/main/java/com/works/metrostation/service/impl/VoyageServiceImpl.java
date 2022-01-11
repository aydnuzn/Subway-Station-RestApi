package com.works.metrostation.service.impl;

import com.works.metrostation.dto.VoyageDateTimeDto;
import com.works.metrostation.dto.VoyageDto;
import com.works.metrostation.exception.EntityNotFoundException;
import com.works.metrostation.model.Metro;
import com.works.metrostation.model.Voyage;
import com.works.metrostation.repository.MetroRepository;
import com.works.metrostation.repository.VoyageRepository;
import com.works.metrostation.service.VoyageService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

@Service
public class VoyageServiceImpl implements VoyageService {

    private static final Logger LOG = Logger.getLogger(VoyageServiceImpl.class);

    private final VoyageRepository voyageRepository;
    private final MetroRepository metroRepository;

    public VoyageServiceImpl(VoyageRepository voyageRepository, MetroRepository metroRepository) {
        this.voyageRepository = voyageRepository;
        this.metroRepository = metroRepository;
    }

    @Override
    public Voyage createVoyage(VoyageDto voyageDto){

        Metro metro = metroRepository.findById(voyageDto.getMetro_id())
                .orElseThrow(() -> new EntityNotFoundException("Metro Not Found"));

        Date date = voyageDto.getDateArrival();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, -2);

        Voyage voyage = Voyage.builder()
                .dateArrival(cal.getTime())
                .metro(metro)
                .build();

        voyage = voyageRepository.save(voyage);
        LOG.info("Voyage Information Created");
        return voyage;
    }

    @Override
    public VoyageDateTimeDto subwayArrivalTime(){

        Voyage voyage = voyageRepository.getNearestVoyage()
                .orElseThrow(() -> new EntityNotFoundException("Voyage Not Found"));

        //    String pattern2 = "yyyy-MM-dd HH:mm";
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        DateFormat df = new SimpleDateFormat(pattern);
        String todayAsString = df.format(voyage.getDateArrival());
        Clock clock = Clock.fixed(Instant.parse(todayAsString), ZoneId.of("Europe/Istanbul"));
        clock = Clock.offset(clock, Duration.ofHours(-2));

        VoyageDateTimeDto voyageDateTimeDto = new VoyageDateTimeDto(clock, voyage.getMetro().getId());
        LOG.info("The closest voyage information is brought");
        return voyageDateTimeDto;
    }

}
