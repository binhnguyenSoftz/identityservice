package com.softz.identity.controller;

import com.softz.identity.dto.ApiResponse;
import com.softz.identity.dto.PermissionDto;
import com.softz.identity.dto.request.NewPermissionRequest;
import com.softz.identity.dto.request.UpdatePermissionRequest;
import com.softz.identity.service.PermissionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping("/permissions")
    public ApiResponse<PermissionDto> createPermission(@RequestBody @Valid NewPermissionRequest permission) {
        return ApiResponse.<PermissionDto>builder()
                .result(permissionService.createPermission(permission))
                .build();
    }

    public ApiResponse<List<PermissionDto>> getPermissions() {
        return ApiResponse.<List<PermissionDto>>builder().result(permissionService.getPermissions()).build();
    }

    @GetMapping("/permissions/{permissionId}")
    public ApiResponse<PermissionDto> getPermissionById(@PathVariable String permissionId) {
        return ApiResponse.<PermissionDto>builder()
                .result(permissionService.getPermissionById(permissionId))
                .build();
    }

    @PatchMapping("/permissions")
    public ApiResponse<PermissionDto> updatePermission(@RequestBody @Valid UpdatePermissionRequest updatePermission) {
        return ApiResponse.<PermissionDto>builder()
                .result(permissionService.updatePermission(updatePermission))
                .build();
    }

    @DeleteMapping("/permissions/{permissionId}")
    public ApiResponse deletePermission(@PathVariable String permissionId) {
        permissionService.deletePermission(permissionId);
        return ApiResponse.builder()
                .build();
    }
}
