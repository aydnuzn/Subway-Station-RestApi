package com.works.metrostation.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class MetroDto {

    @NotNull
    @Range(min = 1, max = 10000)
    private Integer numberOfSeats;

    @NotNull
    @Range(min = 1, max = 100)
    private Integer numberOfDoors;

    @NotNull
    @Range(min = 1, max = Integer.MAX_VALUE)
    private Integer capacity;
}
