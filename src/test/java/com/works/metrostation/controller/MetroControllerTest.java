package com.works.metrostation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.works.metrostation.dto.MetroDto;
import com.works.metrostation.model.Metro;
import com.works.metrostation.repository.UserRepository;
import com.works.metrostation.service.MetroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MetroController.class)
@WithMockUser(value = "admin@mail.com", authorities = {"ADMIN"})
public class MetroControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private MetroService metroService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    ObjectMapper mapper;

    Metro RECORD_METRO = new Metro(1L, 100, 10, 1000);

    @Test
    public void getMetroById_success() throws Exception {

        mockMvc.perform(get("/metros/{metroId}",1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void createMetroById_sucess() throws Exception {

        MetroDto metroDto = MetroDto.builder()
                .numberOfSeats(RECORD_METRO.getNumberOfSeats())
                .numberOfDoors(RECORD_METRO.getNumberOfDoors())
                .capacity(RECORD_METRO.getCapacity())
                .build();

        mockMvc.perform(post("/metros")
                        .content(mapper.writeValueAsString(metroDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteMetroById_success() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/metros/{metroId}",1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void updateMetroRecord_success() throws Exception {

        MetroDto metroDto = MetroDto.builder()
                .numberOfSeats(RECORD_METRO.getNumberOfSeats())
                .numberOfDoors(RECORD_METRO.getNumberOfDoors())
                .capacity(RECORD_METRO.getCapacity())
                .build();

        mockMvc.perform(put("/metros/{metroId}",1)
                        .content(mapper.writeValueAsString(metroDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

}
