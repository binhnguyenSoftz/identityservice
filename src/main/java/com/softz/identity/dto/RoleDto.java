package com.softz.identity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleDto {
    private int id;
    private String name;
    private String description;
    private List<PermissionDto> permissions;
}
