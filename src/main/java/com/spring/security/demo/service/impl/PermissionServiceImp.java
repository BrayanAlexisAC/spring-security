package com.spring.security.demo.service.impl;

import com.spring.security.demo.constants.Constants;
import com.spring.security.demo.model.Permission;
import com.spring.security.demo.repository.PermissionRepository;
import com.spring.security.demo.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImp implements PermissionService {
    private final PermissionRepository permissionRepository;

    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public Optional<Permission> getPermissionById(Long id) {
        return permissionRepository.findById(id);
    }

    @Override
    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Permission updatePermission(Long id, Permission permission) {
        if (permission == null) {
            throw new IllegalArgumentException(Constants.PERMISSION_CANNOT_BE_NULL);
        }

        return permissionRepository.findById(id)
                .map(existingPermission -> {
                    permission.setId(id);
                    return permissionRepository.save(permission);
                })
                .orElse(new Permission());
    }

    @Override
    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
    }

    PermissionServiceImp(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }
}
