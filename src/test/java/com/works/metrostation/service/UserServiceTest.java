package com.works.metrostation.service;

import com.works.metrostation.dto.UserDetailsDto;
import com.works.metrostation.dto.UserDto;
import com.works.metrostation.enumeration.UserRole;
import com.works.metrostation.model.User;
import com.works.metrostation.repository.UserRepository;
import com.works.metrostation.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    User RECORD_1 = new User(1L,"admin@mail.com", "123456", UserRole.ADMIN);
    User RECORD_2 = new User(2L,"yolcu@mail.com", "123456",UserRole.PASSANGER);

    @Test
    public void getUserById_success() throws Exception {

        UserDetailsDto userDetailsDto = new UserDetailsDto(RECORD_1);

        Mockito.when(userRepository.findById(RECORD_2.getId()))
                .thenReturn(Optional.of(RECORD_2));

        assertEquals(userServiceImpl.getUser(userDetailsDto, 2L), RECORD_2);
    }

    @Test
    public void createUserById_sucess() throws Exception {

        UserDto userDto = UserDto.builder()
                .username("demo")
                .password(bCryptPasswordEncoder.encode("123456"))
                .role(UserRole.PASSANGER)
                .build();

        User user = User.builder()
                .id(null)
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .build();

        Mockito.when(userRepository.save(user)).thenReturn(user);

        assertEquals(userServiceImpl.createUser(userDto).getUsername(), user.getUsername());
    }

    @Test
    public void deleteUserById_success() throws Exception {

        Mockito.when(userRepository.findById(RECORD_1.getId()))
                .thenReturn(Optional.of(RECORD_1));

        assertEquals(userServiceImpl.deleteUser(RECORD_1.getId()), RECORD_1);
    }

    @Test
    public void updateUserRecord_success() throws Exception {

        UserDetailsDto userDetailsDto = new UserDetailsDto(RECORD_1);

        UserDto userDto = UserDto.builder()
                .username("aydin")
                .password("123456")
                .build();

        Mockito.when(userRepository.findById(RECORD_2.getId())).thenReturn(Optional.of(RECORD_2));

        User updatedRecord = RECORD_2;
        updatedRecord.setUsername(userDto.getUsername());
        updatedRecord.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        updatedRecord.setRole(userDto.getRole());

        Mockito.when(userRepository.saveAndFlush(RECORD_2)).thenReturn(updatedRecord);

        assertEquals(userServiceImpl.updateUser(userDetailsDto, RECORD_2.getId(), userDto), updatedRecord);
    }


}
