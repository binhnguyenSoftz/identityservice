package com.softz.identity.service.coordinator;

import com.softz.identity.dto.UserDto;
import com.softz.identity.dto.request.NewUserRequest;
import com.softz.identity.entity.Role;
import com.softz.identity.entity.User;
import com.softz.identity.exception.AppException;
import com.softz.identity.exception.ErrorCode;
import com.softz.identity.mapper.UserMapper;
import com.softz.identity.service.RoleService;
import com.softz.identity.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserCoordinatorService {
    UserService userService;
    RoleService roleService;
    UserMapper userMapper;

    public UserDto createUser(NewUserRequest newUserRequest) {
        List<Integer> idList = newUserRequest.getRoles();

        if (idList.stream().distinct().count() != idList.size()) {
            throw new AppException(ErrorCode.ROLE_CONTAINS_DUPLICATED_ITEM);
        }

        List<Role> roles = roleService.getRoles(idList);
        if (idList.size() != roles.size()) {
            throw new AppException(ErrorCode.ROLE_CONTAINS_INVALID_ITEM);
        }

        User user = userMapper.toUser(newUserRequest);

        try {
            user.setRoles(Set.copyOf(roles));

            BCryptPasswordEncoder bCryptPasswordEncoder =
                    new BCryptPasswordEncoder(10);

            long started = System.currentTimeMillis();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            long ended = System.currentTimeMillis();
            log.info("Times to encode: {}", ended - started);

            user = userService.create(user);
        } catch (DataIntegrityViolationException exception) {
            ConstraintViolationException cause =
                    (ConstraintViolationException) exception.getCause();
            throw new AppException(ErrorCode.FIELD_EXISTED, cause.getConstraintName(), cause.getMessage());
        }

        // Mapping to UserDto
        return userMapper.toUserDto(user);
    }
}
