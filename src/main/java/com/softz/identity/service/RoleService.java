package com.softz.identity.service;

import com.softz.identity.dto.PermissionDto;
import com.softz.identity.dto.RoleDto;
import com.softz.identity.dto.request.NewPermissionRequest;
import com.softz.identity.dto.request.NewRoleRequest;
import com.softz.identity.entity.Role;
import com.softz.identity.exception.AppException;
import com.softz.identity.exception.ErrorCode;
import com.softz.identity.mapper.PermissionMapper;
import com.softz.identity.mapper.RoleMapper;
import com.softz.identity.repository.PermissionRepository;
import com.softz.identity.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    public RoleDto createRole(NewRoleRequest roleRequest) {
        try {
            return roleMapper.toRoleDto(roleRepository.save(roleMapper.toPermission(roleRequest)));
        } catch (Exception e ) {
            throw new AppException(ErrorCode.PERMISSION_EXIST);
        }
    }

    public List<RoleDto> getRoles() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleDto)
                .toList();
    }

    public RoleDto getRoleById(String id) {
        return roleMapper.toRoleDto(roleRepository.findById(id).orElseThrow(
                ()->new AppException(ErrorCode.PERMISSION_NOT_FOUND, id)
        ));
    }
}
