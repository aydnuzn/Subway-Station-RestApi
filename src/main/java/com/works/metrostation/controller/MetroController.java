package com.works.metrostation.controller;

import com.works.metrostation.dto.MetroDto;
import com.works.metrostation.model.Metro;
import com.works.metrostation.service.MetroService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(
        value = "/metros",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
public class MetroController {

    private final MetroService metroService;

    public MetroController(MetroService metroService) {
        this.metroService = metroService;
    }

    @PostMapping()
    private Metro createMetro(@RequestBody MetroDto metroDto){
        return metroService.createMetro(metroDto);
    }

    @GetMapping(value = "/{metroId}", consumes = MediaType.ALL_VALUE)
    private Metro getMetro(@PathVariable Long metroId){
        return metroService.getMetro(metroId);
    }

    @PutMapping("/{metroId}")
    private Metro updateMetro(@PathVariable Long metroId, @RequestBody @Valid MetroDto metroDto){
        return metroService.updateMetro(metroId, metroDto);
    }

    @DeleteMapping(value = "/{metroId}", consumes = MediaType.ALL_VALUE)
    private Metro deleteMetro(@PathVariable Long metroId){
        return metroService.deleteMetro(metroId);
    }

}
