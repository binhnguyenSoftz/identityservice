package com.softz.identity.service;

import com.softz.identity.dto.PermissionDto;
import com.softz.identity.dto.request.NewPermissionRequest;
import com.softz.identity.dto.request.UpdatePermissionRequest;
import com.softz.identity.entity.Permission;
import com.softz.identity.exception.AppException;
import com.softz.identity.exception.ErrorCode;
import com.softz.identity.mapper.PermissionMapper;
import com.softz.identity.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionDto createPermission(NewPermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);

        return permissionMapper.toPermissionDto(permission);
    }

    public List<PermissionDto> getPermissions() {
        return permissionRepository.findAll()
                .stream()
                .map(permissionMapper::toPermissionDto)
                .toList();
    }

    public PermissionDto updatePermission(int id, UpdatePermissionRequest request) {
        var permission = permissionRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

        permissionMapper.updatePermission(permission, request);

        return permissionMapper.toPermissionDto(permissionRepository.save(permission));
    }

    public void deletePermission(int id) {
        permissionRepository.deleteById(id);
    }

    public List<Permission> getPermissions(List<Integer> permissions) {
        return permissionRepository.findByIdIn(permissions);
    }
}
