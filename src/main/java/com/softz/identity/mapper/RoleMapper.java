package com.softz.identity.mapper;

import com.softz.identity.dto.RoleDto;
import com.softz.identity.dto.request.NewRoleRequest;
import com.softz.identity.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toRoleDto(Role role);

    @Mapping(target = "permissions", ignore = true)
    Role toRole(NewRoleRequest request);
}
