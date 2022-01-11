package com.works.metrostation.service;

import com.works.metrostation.dto.MetroDto;
import com.works.metrostation.model.Metro;
import com.works.metrostation.repository.MetroRepository;
import com.works.metrostation.service.impl.MetroServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MetroServiceTest {

    @InjectMocks
    private MetroServiceImpl metroServiceImpl;

    @Mock
    private MetroRepository metroRepository;

    Metro RECORD_1 = new Metro(1L, 100, 10, 1000);
    Metro RECORD_2 = new Metro(2L, 200, 20, 1500);

    @Test
    public void getMetroById_success() throws Exception {

        Mockito.when(metroRepository.findById(RECORD_1.getId()))
                        .thenReturn(Optional.of(RECORD_1));

        assertEquals(metroServiceImpl.getMetro(RECORD_1.getId()), RECORD_1);
    }

    @Test
    public void createMetroById_sucess() throws Exception {

        MetroDto metroDto = MetroDto.builder()
                .numberOfSeats(RECORD_1.getNumberOfSeats())
                .numberOfDoors(RECORD_1.getNumberOfDoors())
                .capacity(RECORD_1.getCapacity())
                .build();

        Metro metro = RECORD_1;
        metro.setId(null);

        Mockito.when(metroRepository.save(metro)).thenReturn(metro);

        assertEquals(metroServiceImpl.createMetro(metroDto), RECORD_1);
    }

    @Test
    public void deleteMetroById_success() throws Exception {

        Mockito.when(metroRepository.findById(RECORD_1.getId())).thenReturn(Optional.of(RECORD_1));

        assertEquals(metroServiceImpl.deleteMetro(RECORD_1.getId()), RECORD_1);
    }

    @Test
    public void updateMetroRecord_success() throws Exception {

        MetroDto metroDto = MetroDto.builder()
                .numberOfSeats(RECORD_2.getNumberOfSeats())
                .numberOfDoors(RECORD_2.getNumberOfDoors())
                .capacity(RECORD_2.getCapacity())
                .build();

        Mockito.when(metroRepository.findById(RECORD_1.getId())).thenReturn(Optional.of(RECORD_1));

        Metro updatedMetro = RECORD_1;
        updatedMetro.setNumberOfSeats(RECORD_2.getNumberOfSeats());
        updatedMetro.setNumberOfDoors(RECORD_2.getNumberOfDoors());
        updatedMetro.setCapacity(RECORD_2.getCapacity());

        Mockito.when(metroRepository.saveAndFlush(updatedMetro)).thenReturn(updatedMetro);

        assertEquals(metroServiceImpl.updateMetro(RECORD_1.getId(), metroDto), updatedMetro);
    }

}
