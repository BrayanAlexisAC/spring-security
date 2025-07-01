package com.spring.security.demo.service;

import com.spring.security.demo.model.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionService {

    /**
     * Method to get all permissions.
     *
     * @return List of all permissions.
     */
    List<Permission> getAllPermissions();

    /**
     * Method to get a permission by its ID.
     *
     * @param id ID of the permission.
     * @return Permission object if found, null otherwise.
     */
    Optional<Permission> getPermissionById(Long id);

    /**
     * Method to create a new permission.
     *
     * @param permission Permission object to be created.
     * @return Created Permission object.
     */
    Permission createPermission(Permission permission);

    /**
     * Method to update an existing permission.
     *
     * @param id ID of the permission to be updated.
     * @param permission Updated Permission object.
     * @return Updated Permission object if found, null otherwise.
     */
    Permission updatePermission(Long id, Permission permission);

    /**
     * Method to delete a permission by its ID.
     *
     * @param id ID of the permission to be deleted.
     */
    void deletePermission(Long id);
}
