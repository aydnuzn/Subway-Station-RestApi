package com.works.metrostation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.works.metrostation.dto.VoyageDto;
import com.works.metrostation.repository.UserRepository;
import com.works.metrostation.service.VoyageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.text.SimpleDateFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VoyageController.class)
@WithMockUser(value = "admin@mail.com", authorities = {"ADMIN"})
public class VoyageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private VoyageService voyageService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void subwayArrivalTime_success() throws Exception {

        mockMvc.perform(get("/voyages/arrivaltime")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void createCardById_success() throws Exception {

        VoyageDto voyageDto = new VoyageDto(
                1L, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-12 18:30:00")
        );

        mockMvc.perform(post("/voyages")
                        .content(mapper.writeValueAsString(voyageDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

}
