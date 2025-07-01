package com.spring.security.demo.service;

import com.spring.security.demo.model.SSUser;

import java.util.List;

public interface UserService {
    /**
     * Method to get all users.
     *
     * @return List of all users.
     */
    List<SSUser> getAllUsers();

    /**
     * Method to get a user by their ID.
     *
     * @param id ID of the user.
     * @return User object if found, null otherwise.
     */
    SSUser getUserById(Long id);

    /**
     * Method to create a new user.
     *
     * @param user User object to be created.
     * @return Created User object.
     */
    SSUser createUser(SSUser user);

    /**
     * Method to update an existing user.
     *
     * @param id ID of the user to be updated.
     * @param user Updated User object.
     * @return Updated User object if found, null otherwise.
     */
    SSUser updateUser(Long id, SSUser user);

    /**
     * Method to delete a user by their ID.
     *
     * @param id ID of the user to be deleted.
     */
    void deleteUserById(Long id);
}
