package com.softz.identity.controller;

import com.softz.identity.dto.UserDto;
import com.softz.identity.dto.request.NewUserRequest;
import com.softz.identity.entity.User;
import com.softz.identity.service.UserService;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    UserDto createUser(@RequestBody NewUserRequest user) {
        return userService.createUser(user);
    }

    @GetMapping("/users")
    List<UserDto> getUser() {
        return userService.getUsers();
    }

    @GetMapping("/user/{userId}")
    UserDto getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/user/filter")
    UserDto getUserByUsername(@PathParam("username") String username) {
        return userService.getUserByUserName(username);
    }
}
