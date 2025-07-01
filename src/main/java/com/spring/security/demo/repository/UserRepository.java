package com.spring.security.demo.repository;

import com.spring.security.demo.model.SSUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SSUser, Long> {

    /**
     * Method to find a user by their username.
     *
     * @param username Username of the user.
     * @return User object if found, null otherwise.
     */
    Optional<SSUser> findByUsername(String username);

    /**
     * Method to check if a user exists by their username.
     *
     * @param username Username of the user.
     * @return True if the user exists, false otherwise.
     */
    boolean existsByUsername(String username);
}
