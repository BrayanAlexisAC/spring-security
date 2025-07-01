package com.spring.security.demo.service.impl;

import com.spring.security.demo.model.Role;
import com.spring.security.demo.repository.RoleRepository;
import com.spring.security.demo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<String> getAllRoles() {
        var roles = roleRepository.findAll();
        return roles.stream().map(Role::getName).toList();
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Long id, Role role) {
        return roleRepository.findById(id)
                .map(existingRole -> {
                    role.setId(existingRole.getId());
                    return roleRepository.save(role);
                }).orElse(new Role());
    }

    @Override
    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }

    RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
