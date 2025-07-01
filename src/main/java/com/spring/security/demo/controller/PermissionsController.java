package com.spring.security.demo.controller;

import com.spring.security.demo.model.Permission;
import com.spring.security.demo.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/permissions")
public class PermissionsController {

    private final PermissionService permissionService;

    @GetMapping("/all")
    public ResponseEntity<List<Permission>> getAllPermissions() {
        var permissions = permissionService.getAllPermissions();
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable Long id) {
        var permission = permissionService.getPermissionById(id);
        return permission.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) {
        if (Objects.isNull(permission)) {
            return ResponseEntity.badRequest().build();
        }
        var createdPermission = permissionService.createPermission(permission);
        return ResponseEntity.ok(createdPermission);
    }

    public PermissionsController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
}
