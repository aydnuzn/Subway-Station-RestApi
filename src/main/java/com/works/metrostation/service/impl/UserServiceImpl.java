package com.works.metrostation.service.impl;

import com.works.metrostation.dto.UserDetailsDto;
import com.works.metrostation.dto.UserDto;
import com.works.metrostation.enumeration.UserRole;
import com.works.metrostation.exception.AccessDeniedException;
import com.works.metrostation.exception.EntityNotFoundException;
import com.works.metrostation.model.User;
import com.works.metrostation.repository.UserRepository;
import com.works.metrostation.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /*
     *  Create user can be called by anyone
     */
    @Override
    public User createUser(UserDto userDto){

        User user = User.builder()
                .username(userDto.getUsername())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .role(userDto.getRole())
                .build();
        return userRepository.save(user);
    }

    @Override
    public User getUser(UserDetailsDto userDetailsDto, Long userId){

        checkUserPermission(userDetailsDto, userId);
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    @Override
    public User updateUser(UserDetailsDto userDetailsDto, Long userId, UserDto userDto){

        checkUserPermission(userDetailsDto, userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Entity Not Found"));
        user.setUsername(userDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User deleteUser(Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Entity Not Found"));
        userRepository.delete(user);
        return user;
    }



    /*
     * Strict security policy implemented, admin can take all actions and users can only import or modify their own account
     */
    private static void checkUserPermission(UserDetailsDto userDetailsDto, Long userId) {
        if (!userDetailsDto.getUser().getId().equals(userId) && !userDetailsDto.getUser().getRole().equals(UserRole.ADMIN)) {
            throw new AccessDeniedException("Access Denied");
        }
    }


}
