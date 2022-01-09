package com.works.metrostation.controller;

import com.works.metrostation.dto.BoardingResponseDto;
import com.works.metrostation.dto.UserDetailsDto;
import com.works.metrostation.service.MetroStationService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class MetroStationController {

    private final MetroStationService metroStationService;

    public MetroStationController(MetroStationService metroStationService) {
        this.metroStationService = metroStationService;
    }

    @PostMapping("/boarding")
    private BoardingResponseDto entranceToSubway(@AuthenticationPrincipal UserDetailsDto userDetailsDto){
        return metroStationService.entranceToSubway(userDetailsDto);
    }

}
