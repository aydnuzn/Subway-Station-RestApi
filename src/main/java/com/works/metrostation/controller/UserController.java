package com.works.metrostation.controller;

import com.works.metrostation.dto.UserDetailsDto;
import com.works.metrostation.dto.UserDto;
import com.works.metrostation.model.User;
import com.works.metrostation.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(
        value = "/users",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    private User createUser(@RequestBody @Valid UserDto userDto){
        return userService.createUser(userDto);
    }

    @GetMapping("/{userId}")
    private User getUser(@AuthenticationPrincipal UserDetailsDto userDetailsDto, @PathVariable Long userId){
        return userService.getUser(userDetailsDto, userId);
    }

    @PutMapping("/{userId}")
    private User updateUser(
            @AuthenticationPrincipal UserDetailsDto userDetailsDto,
            @PathVariable Long userId, @RequestBody @Valid UserDto userDto){
        return userService.updateUser(userDetailsDto, userId, userDto);
    }

    @DeleteMapping(value = "/{userId}", consumes = MediaType.ALL_VALUE)
    private User deleteUser(@PathVariable Long userId){
        return userService.deleteUser(userId);
    }

}
