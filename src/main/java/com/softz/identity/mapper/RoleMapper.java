package com.softz.identity.mapper;

import com.softz.identity.dto.PermissionDto;
import com.softz.identity.dto.RoleDto;
import com.softz.identity.dto.request.NewPermissionRequest;
import com.softz.identity.dto.request.NewRoleRequest;
import com.softz.identity.entity.Permission;
import com.softz.identity.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toRoleDto(Role role);
    Role toPermission(NewRoleRequest newRoleRequest);

}
