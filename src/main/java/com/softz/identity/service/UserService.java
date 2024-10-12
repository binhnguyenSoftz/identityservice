package com.softz.identity.service;

import com.softz.identity.dto.ApiResponse;
import com.softz.identity.dto.UserDto;
import com.softz.identity.dto.request.NewUserRequest;
import com.softz.identity.exception.AppException;
import com.softz.identity.exception.ErrorCode;
import com.softz.identity.mapper.UserMapper;
import com.softz.identity.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            return userMapper.toUserDto(userRepository.save(userMapper.toUser(user)));
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
    }

    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    public UserDto getUserById(String id) {
        return userMapper.toUserDto(userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    public UserDto getUserByUserName(String userName) {
        return userMapper.toUserDto(userRepository.findByUsername(userName).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }
}
