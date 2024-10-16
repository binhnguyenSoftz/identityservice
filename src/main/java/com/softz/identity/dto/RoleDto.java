package com.softz.identity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RoleDto {
    private String id;
    private String name;
    private String description;
    private PermissionDto[] permissions;
}
