package com.works.metrostation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.works.metrostation.repository.UserRepository;
import com.works.metrostation.service.MetroStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MetroStationController.class)
@WithMockUser(value = "passanger@mail.com", authorities = {"PASSANGER"})
public class MetroStationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private MetroStationService metroStationService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void entranceToSubway_success() throws Exception {

        mockMvc.perform(post("/boarding")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

}
