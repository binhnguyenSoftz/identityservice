package com.softz.identity.dto.request;

import com.softz.identity.dto.PermissionDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewRoleRequest {
    @NotNull
    private String name;
    private String description;
    private PermissionDto[] permissions;
}
