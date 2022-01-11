package com.works.metrostation.controller;

import com.works.metrostation.dto.VoyageDateTimeDto;
import com.works.metrostation.dto.VoyageDto;
import com.works.metrostation.model.Voyage;
import com.works.metrostation.service.VoyageService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(
        value = "/voyages",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
public class VoyageController {

    private final VoyageService voyageService;

    public VoyageController(VoyageService voyageService) {
        this.voyageService = voyageService;
    }

    @GetMapping(value = "/arrivaltime", consumes = MediaType.ALL_VALUE)
    public VoyageDateTimeDto subwayArrivalTime() {
        return voyageService.subwayArrivalTime();
    }

    @PostMapping
    public Voyage createVoyage(@RequestBody @Valid VoyageDto voyageDto) {
        return voyageService.createVoyage(voyageDto);
    }

}
