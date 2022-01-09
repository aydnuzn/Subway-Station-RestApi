package com.works.metrostation.service;

import com.works.metrostation.dto.UserDetailsDto;
import com.works.metrostation.dto.UserDto;
import com.works.metrostation.model.User;

public interface UserService {

    User createUser(UserDto userDto);

    User getUser(UserDetailsDto userDetailsDto, Long userId);

    User updateUser(UserDetailsDto userDetailsDto, Long userId, UserDto userDto);

    User deleteUser(Long userId);

}
