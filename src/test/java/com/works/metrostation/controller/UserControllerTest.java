package com.works.metrostation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.works.metrostation.dto.UserDto;
import com.works.metrostation.enumeration.UserRole;
import com.works.metrostation.repository.UserRepository;
import com.works.metrostation.service.UserService;
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

@WebMvcTest(UserController.class)
@WithMockUser(value = "admin@mail.com", authorities = {"ADMIN"})
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void getUserById_success() throws Exception {

        mockMvc.perform(get("/users/{userId}",1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void createUserById_success() throws Exception {

        UserDto userDto = new UserDto("admin2@mail.com", "123456", UserRole.ADMIN);

        mockMvc.perform(post("/users")
                .content(mapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserById_success() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/{userId}",1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUsersRecord_success() throws Exception {

        UserDto userDto = new UserDto("admin2@mail.com", "123456", UserRole.ADMIN);

        mockMvc.perform(put("/users/{userId}",1)
                        .content(mapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

}
