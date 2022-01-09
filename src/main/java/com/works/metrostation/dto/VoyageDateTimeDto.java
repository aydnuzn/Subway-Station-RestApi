package com.works.metrostation.dto;

import lombok.Data;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class VoyageDateTimeDto {

    private LocalDate arrivalDate;

    private LocalTime arrivalTime;

    private Long metroId;

    public VoyageDateTimeDto(Clock clock, Long metroId) {
        arrivalDate = LocalDate.now(clock);
        arrivalTime = LocalTime.now(clock);
        this.metroId = metroId;
    }
}
