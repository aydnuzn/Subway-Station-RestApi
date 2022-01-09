package com.works.metrostation.controller;

import com.works.metrostation.dto.VoyageDateTimeDto;
import com.works.metrostation.dto.VoyageDto;
import com.works.metrostation.model.Voyage;
import com.works.metrostation.repository.VoyageRepository;
import com.works.metrostation.service.VoyageService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(
        value = "/voyages"
)
public class VoyageController {

    private final VoyageService voyageService;
    private final VoyageRepository voyageRepository;

    public VoyageController(VoyageService voyageService, VoyageRepository voyageRepository) {
        this.voyageService = voyageService;
        this.voyageRepository = voyageRepository;
    }

    @GetMapping("/arrivaltime")
    public VoyageDateTimeDto subwayArrivalTime() {
        return voyageService.subwayArrivalTime();
    }

    @PostMapping
    public Voyage createVoyage(@RequestBody @Valid VoyageDto voyageDto) {
        return voyageService.createVoyage(voyageDto);
    }

}
