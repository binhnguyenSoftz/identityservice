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

    public PermissionDto createPermission(NewPermissionRequest permissionRequest) {
        try {
            return permissionMapper.toPermissionDto(permissionRepository.save(permissionMapper.toPermission(permissionRequest)));
        } catch (Exception e ) {
            throw new AppException(ErrorCode.PERMISSION_EXIST);
        }
    }

    public List<PermissionDto> getPermissions() {
        return permissionRepository.findAll()
                .stream()
                .map(permissionMapper::toPermissionDto)
                .toList();
    }

    public PermissionDto getPermissionById(String id) {
        return permissionMapper.toPermissionDto(permissionRepository.findById(id).orElseThrow(
                ()->new AppException(ErrorCode.PERMISSION_NOT_FOUND, id)
        ));
    }

    public PermissionDto updatePermission(UpdatePermissionRequest permissionRequest) {
        try {
            return permissionMapper.toPermissionDto(permissionRepository.save(permissionMapper.toPermission(permissionRequest)));
        } catch (Exception e ) {
            throw new AppException(ErrorCode.PERMISSION_EXIST);
        }
    }

    public void deletePermission(String id) {
        try {
            permissionRepository.deleteById(id);
        } catch (Exception e ) {
            throw new AppException(ErrorCode.PERMISSION_EXIST);
        }
    }
}
