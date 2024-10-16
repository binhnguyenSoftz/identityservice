package com.softz.identity.mapper;

import com.softz.identity.dto.PermissionDto;
import com.softz.identity.dto.UserDto;
import com.softz.identity.dto.request.NewPermissionRequest;
import com.softz.identity.dto.request.NewUserRequest;
import com.softz.identity.dto.request.UpdatePermissionRequest;
import com.softz.identity.entity.Permission;
import com.softz.identity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionDto toPermissionDto(Permission permission);
    Permission toPermission(NewPermissionRequest newPermissionRequest);
    Permission toPermission(UpdatePermissionRequest updatePermissionRequest);

}
