package com.softz.identity.service.coordinator;

import com.softz.identity.dto.RoleDto;
import com.softz.identity.dto.request.NewRoleRequest;
import com.softz.identity.entity.Permission;
import com.softz.identity.entity.Role;
import com.softz.identity.exception.AppException;
import com.softz.identity.exception.ErrorCode;
import com.softz.identity.mapper.RoleMapper;
import com.softz.identity.service.PermissionService;
import com.softz.identity.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleCoordinatorService {
    PermissionService permissionService;
    RoleService roleService;
    RoleMapper roleMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public RoleDto createRole(NewRoleRequest request) {
        List<Integer> idList = request.getPermissions();
        List<Permission> permissions = permissionService.getPermissions(
                CollectionUtils.isEmpty(idList) ? Collections.emptyList() : idList);

        // Validate
        if (!CollectionUtils.isEmpty(request.getPermissions())
                && idList.size() != permissions.size()) {
            throw new AppException(ErrorCode.INVALID_INPUT);
        }

        Role role = roleMapper.toRole(request);
        role.setPermissions(Set.copyOf(permissions));

        return roleMapper.toRoleDto(roleService.create(role));
    }
}
