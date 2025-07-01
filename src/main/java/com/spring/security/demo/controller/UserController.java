package com.spring.security.demo.controller;

import com.spring.security.demo.model.Role;
import com.spring.security.demo.model.SSUser;
import com.spring.security.demo.service.RoleService;
import com.spring.security.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/all")
    public ResponseEntity<List<SSUser>> getAllUsers() {
        var users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SSUser> getUserById(@PathVariable Long id) {
        var user = userService.getUserById(id);
        return Objects.nonNull(user.getId()) ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<SSUser> createUser(@RequestBody SSUser ssUser) {
        var roleList = new HashSet<Role>();

        ssUser.getLstRoles().forEach(role -> {
            var roleEntity = roleService.getRoleById(role.getId());
            roleEntity.ifPresent(roleList::add);
        });

        if (!roleList.isEmpty()){
            ssUser.setLstRoles(roleList);
            var createdUser = userService.createUser(ssUser);
            return ResponseEntity.ok(createdUser);
        }

        return ResponseEntity.badRequest().build();
    }

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

}
