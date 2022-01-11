package com.works.metrostation.service.impl;

import com.works.metrostation.dto.MetroDto;
import com.works.metrostation.exception.EntityNotFoundException;
import com.works.metrostation.model.Metro;
import com.works.metrostation.repository.MetroRepository;
import com.works.metrostation.service.MetroService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MetroServiceImpl implements MetroService {

    private static final Logger LOG = Logger.getLogger(MetroServiceImpl.class);

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

        metro = metroRepository.save(metro);
        LOG.info("Metro Created");
        return metro;
    }

    @Override
    public Metro getMetro(Long metroId){

        Metro metro = metroRepository.findById(metroId)
                .orElseThrow(() -> new EntityNotFoundException("Metro not found"));
        LOG.info("Metro info Retrieved Successfully");
        return metro;
    }

    @Override
    public Metro updateMetro(Long metroId, MetroDto metroDto){

        Metro metro = metroRepository.findById(metroId)
                        .orElseThrow(() -> new EntityNotFoundException("Metro Not Found"));
        metro.setNumberOfDoors(metroDto.getNumberOfDoors());
        metro.setNumberOfSeats(metroDto.getNumberOfSeats());
        metro.setCapacity(metroDto.getCapacity());

        metro = metroRepository.saveAndFlush(metro);
        LOG.info("Metro Updated");
        return metro;
    }

    @Override
    public Metro deleteMetro(Long metroId){

        Metro metro = metroRepository.findById(metroId)
                .orElseThrow(() -> new EntityNotFoundException("Metro Not Found"));

        metroRepository.delete(metro);
        LOG.info("Metro Deleted");
        return metro;
    }

}
