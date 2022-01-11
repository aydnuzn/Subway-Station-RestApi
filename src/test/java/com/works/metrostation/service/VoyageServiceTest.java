package com.works.metrostation.service;

import com.works.metrostation.dto.VoyageDateTimeDto;
import com.works.metrostation.dto.VoyageDto;
import com.works.metrostation.model.Metro;
import com.works.metrostation.model.Voyage;
import com.works.metrostation.repository.MetroRepository;
import com.works.metrostation.repository.VoyageRepository;
import com.works.metrostation.service.impl.VoyageServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class VoyageServiceTest {

    @InjectMocks
    private VoyageServiceImpl voyageServiceImpl;

    @Mock
    private VoyageRepository voyageRepository;

    @Mock
    private MetroRepository metroRepository;

    Metro RECORD_SUBWAY = new Metro(1L, 100, 10, 1000);

    @Test
    public void createCardById_success() throws Exception {

        Mockito.when(metroRepository.findById(RECORD_SUBWAY.getId()))
                .thenReturn(Optional.of(RECORD_SUBWAY));

        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-12 18:30:00");
        VoyageDto voyageDto = new VoyageDto();
        voyageDto.setDateArrival(date);
        voyageDto.setMetro_id(RECORD_SUBWAY.getId());

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, -2);

        Voyage voyage = Voyage.builder()
                .dateArrival(cal.getTime())
                .metro(RECORD_SUBWAY)
                .build();

        Mockito.when(voyageRepository.save(voyage)).thenReturn(voyage);

        Assertions.assertEquals(voyageServiceImpl.createVoyage(voyageDto), voyage);
    }

    @Test
    public void subwayArrivalTime_success() throws Exception {
        Voyage voyage = new Voyage(
                1L,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-12 18:30:00"),
                RECORD_SUBWAY);

        Mockito.when(voyageRepository.getNearestVoyage()).thenReturn(Optional.of(voyage));

        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        DateFormat df = new SimpleDateFormat(pattern);
        String todayAsString = df.format(voyage.getDateArrival());
        Clock clock = Clock.fixed(Instant.parse(todayAsString), ZoneId.of("Europe/Istanbul"));
        clock = Clock.offset(clock, Duration.ofHours(-2));

        VoyageDateTimeDto voyageDateTimeDto = new VoyageDateTimeDto(clock, voyage.getMetro().getId());

        assertEquals(voyageServiceImpl.subwayArrivalTime(), voyageDateTimeDto);

    }

}
