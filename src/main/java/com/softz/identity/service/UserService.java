package com.softz.identity.service;

import com.softz.identity.dto.UserDto;
import com.softz.identity.dto.request.NewUserRequest;
import com.softz.identity.entity.User;
import com.softz.identity.mapper.UserMapper;
import com.softz.identity.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    public UserDto createUser(NewUserRequest user) {
        return userMapper.toUserDto(userRepository.save(userMapper.toUser(user)));
    }

    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    public UserDto getUserById(String id) {
        return userMapper.toUserDto(userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        ));
    }

    public UserDto getUserByUserName(String userName) {
        return userMapper.toUserDto(userRepository.findByUsername(userName).orElseThrow(
                () -> new RuntimeException("User not found")
        ));
    }
}
