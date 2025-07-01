package com.spring.security.demo.service;

import com.spring.security.demo.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    /**
     * Method to get all roles.
     *
     * @return List of all roles.
     */
    List<String> getAllRoles();

    /**
     * Method to get a role by its ID.
     *
     * @param id ID of the role.
     * @return Role object if found, null otherwise.
     */
    Optional<Role> getRoleById(Long id);

    /**
     * Method to create a new role.
     *
     * @param role Role object to be created.
     * @return Created Role object.
     */
    Role createRole(Role role);

    /**
     * Method to update an existing role.
     *
     * @param id ID of the role to be updated.
     * @param role Updated Role object.
     * @return Updated Role object if found, null otherwise.
     */
    Role updateRole(Long id, Role role);

    /**
     * Method to delete a role by its ID.
     *
     * @param id ID of the role to be deleted.
     */
    void deleteRoleById(Long id);
}
