package com.spring.security.demo.repository;

import com.spring.security.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Method to find a role by its name.
     *
     * @param name Name of the role.
     * @return Role object if found, null otherwise.
     */
    Optional<Role> findByName(String name);
}
