package com.works.metrostation.service.impl;

import com.works.metrostation.dto.MetroDto;
import com.works.metrostation.exception.EntityNotFoundException;
import com.works.metrostation.model.Metro;
import com.works.metrostation.repository.MetroRepository;
import com.works.metrostation.service.MetroService;
import org.springframework.stereotype.Service;

@Service
public class MetroServiceImpl implements MetroService {

    private final MetroRepository metroRepository;

    public MetroServiceImpl(MetroRepository metroRepository) {
        this.metroRepository = metroRepository;
    }

    @Override
    public Metro createMetro(MetroDto metroDto){

        Metro metro = Metro.builder()
                .numberOfDoors(metroDto.getNumberOfDoors())
                .numberOfSeats(metroDto.getNumberOfSeats())
                .capacity(metroDto.getCapacity())
                .build();
        return metroRepository.save(metro);
    }

    @Override
    public Metro getMetro(Long metroId){

        return this.metroRepository.findById(metroId)
                .orElseThrow(() -> new EntityNotFoundException("Metro not found"));
    }

    @Override
    public Metro updateMetro(Long metroId, MetroDto metroDto){

        Metro metro = metroRepository.findById(metroId)
                        .orElseThrow(() -> new EntityNotFoundException("Metro Not Found"));
        metro.setNumberOfDoors(metroDto.getNumberOfDoors());
        metro.setNumberOfSeats(metroDto.getNumberOfSeats());
        metro.setCapacity(metroDto.getCapacity());
        return metroRepository.saveAndFlush(metro);
    }

    @Override
    public Metro deleteMetro(Long metroId){

        Metro metro = metroRepository.findById(metroId)
                .orElseThrow(() -> new EntityNotFoundException("Metro Not Found"));
        metroRepository.delete(metro);
        return metro;
    }

}
