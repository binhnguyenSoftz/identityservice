package com.softz.identity.controller;

import com.softz.identity.dto.ApiResponse;
import com.softz.identity.dto.UserDto;
import com.softz.identity.dto.request.NewUserRequest;
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
    public ApiResponse<UserDto> createUser(@RequestBody NewUserRequest newUserRequest) {
        var userDto = userService.createUser(newUserRequest);
        return ApiResponse.<UserDto>builder()
                .result(userDto)
                .build();
    }

    @GetMapping("/users")
    ApiResponse<List<UserDto>> getUser() {
        return ApiResponse.<List<UserDto>>builder().result(userService.getUsers()).build();
    }

    @GetMapping("/user/{userId}")
    ApiResponse<UserDto> getUserById(@PathVariable String userId) {
        return ApiResponse.<UserDto>builder().result(userService.getUserById(userId)).build();
    }

    @GetMapping("/user/filter")
    ApiResponse<UserDto> getUserByUsername(@PathParam("username") String username) {
        return ApiResponse.<UserDto>builder().result(userService.getUserByUserName(username)).build();
    }
}
