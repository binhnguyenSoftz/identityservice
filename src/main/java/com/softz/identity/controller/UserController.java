package com.softz.identity.controller;

import com.softz.identity.dto.ApiResponse;
import com.softz.identity.dto.UserDto;
import com.softz.identity.dto.request.NewUserRequest;
import com.softz.identity.entity.User;
import com.softz.identity.service.UserService;
import com.softz.identity.service.coordinator.UserCoordinatorService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserCoordinatorService userCoordinatorService;
    UserService userService;

    @PostMapping("/users/registration")
    public ApiResponse<UserDto> createUser(@RequestBody @Valid NewUserRequest newUserRequest) {
        var userDto = userCoordinatorService.createUser(newUserRequest);
        return ApiResponse.<UserDto>builder().result(userDto).build();
    }

    @GetMapping("/users")
    public ApiResponse<List<UserDto>> getUsers() {
        List<UserDto> users = userService.getUsers();
        return ApiResponse.<List<UserDto>>builder().result(users).build();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") String userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/users/filter")
    public User getUserByUsername(@Param("username") String username) {
        return userService.getUserByUsername(username);
    }
}
