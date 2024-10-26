package com.softz.identity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String userId;
    private String username;
    private String email;
    private List<RoleDto> roles;
}
