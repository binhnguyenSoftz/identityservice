package com.softz.identity.repository;

import com.softz.identity.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    List<Permission> findByIdIn(Collection<Integer> ids);
}
