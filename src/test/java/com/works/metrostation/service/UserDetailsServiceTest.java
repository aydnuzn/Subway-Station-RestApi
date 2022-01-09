package com.works.metrostation.service;

import com.works.metrostation.dto.UserDetailsDto;
import com.works.metrostation.enumeration.UserRole;
import com.works.metrostation.model.User;
import com.works.metrostation.repository.UserRepository;
import com.works.metrostation.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailSerciveImpl;

    @Mock
    private UserRepository userRepository;

    User RECORD_1 = new User(1L,"yolcu@mail.com", "123456", UserRole.PASSANGER);

    @Test
    public void loadUserByUsername_success() throws Exception {
        Mockito.when(userRepository.findTopByUsername(RECORD_1.getUsername()))
                .thenReturn(Optional.of(RECORD_1));

        assertEquals(userDetailSerciveImpl.loadUserByUsername(RECORD_1.getUsername()), new UserDetailsDto(RECORD_1));
    }

}
