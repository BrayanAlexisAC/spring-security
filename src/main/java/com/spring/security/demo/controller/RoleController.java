package com.spring.security.demo.controller;

import com.spring.security.demo.model.Permission;
import com.spring.security.demo.model.Role;
import com.spring.security.demo.service.PermissionService;
import com.spring.security.demo.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;
    private final PermissionService permissionService;

    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllRoles() {
        var roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        var role = roleService.getRoleById(id);
        return role.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Set<Permission> permissionList = new HashSet<>();

        role.getPermissions().forEach(permission -> {
            var permissionEntity = permissionService.getPermissionById(permission.getId());
            permissionEntity.ifPresent(permissionList::add);
        });

        role.setPermissions(permissionList);
        var newRole = roleService.createRole(role);
        return ResponseEntity.ok(newRole);
    }

    public RoleController(RoleService roleService, PermissionService permissionService) {
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

}
