package com.softz.identity.repository;

import com.softz.identity.entity.Permission;
import com.softz.identity.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
