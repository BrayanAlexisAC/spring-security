package com.spring.security.demo.repository;

import com.spring.security.demo.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    /**
     * Method to find a permission by its name.
     *
     * @param name Name of the permission.
     * @return Permission object if found, null otherwise.
     */
    Optional<Permission> findByName(String name);
}
