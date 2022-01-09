package com.works.metrostation.service.impl;

import com.works.metrostation.dto.UserDetailsDto;
import com.works.metrostation.model.User;
import com.works.metrostation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findTopByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
        return new UserDetailsDto(user);
    }

}
