package com.softz.identity.service;

import com.softz.identity.dto.RoleDto;
import com.softz.identity.entity.Role;
import com.softz.identity.mapper.RoleMapper;
import com.softz.identity.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleDto> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleDto).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Role> getRoles(List<Integer> roles) {
        return roleRepository.findByIdIn(roles);
    }
}
