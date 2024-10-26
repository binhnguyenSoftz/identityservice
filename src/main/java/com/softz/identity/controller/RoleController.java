package com.softz.identity.controller;

import com.softz.identity.dto.ApiResponse;
import com.softz.identity.dto.RoleDto;
import com.softz.identity.dto.request.NewRoleRequest;
import com.softz.identity.service.RoleService;
import com.softz.identity.service.coordinator.RoleCoordinatorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleCoordinatorService roleCoordinatorService;
    RoleService roleService;

    @PostMapping("/roles")
    public ApiResponse<RoleDto> createRole(@RequestBody NewRoleRequest newRoleRequest) {
        var roleDto = roleCoordinatorService.createRole(newRoleRequest);
        return ApiResponse.<RoleDto>builder().result(roleDto).build();
    }

    @GetMapping("/roles")
    public ApiResponse<List<RoleDto>> getAll() {
        var roles = roleService.getAll();
        return ApiResponse.<List<RoleDto>>builder().result(roles).build();
    }
}
