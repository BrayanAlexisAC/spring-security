package com.spring.security.demo.controller;

import com.spring.security.demo.model.Role;
import com.spring.security.demo.model.SSUser;
import com.spring.security.demo.service.RoleService;
import com.spring.security.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("isAuthenticated()") // Ensures that only authenticated users can access these endpoints
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<List<SSUser>> getAllUsers() {
        var users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<SSUser> getUserById(@PathVariable Long id) {
        var user = userService.getUserById(id);
        return Objects.nonNull(user.getId()) ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<SSUser> createUser(@RequestBody SSUser ssUser) {
        var roleList = new HashSet<Role>();

        ssUser.getLstRoles().forEach(role -> {
            var roleEntity = roleService.getRoleById(role.getId());
            roleEntity.ifPresent(roleList::add);
        });

        if (!roleList.isEmpty()){
            ssUser.setPassword(userService.encodePassword(ssUser.getPassword()));
            ssUser.setLstRoles(roleList);
            var createdUser = userService.createUser(ssUser);
            return ResponseEntity.ok(createdUser);
        }

        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/password/{id}")
    @PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<SSUser> updateUserPassword(@PathVariable Long id, @RequestParam("newPassword") String newPassword) {
        var user = userService.getUserById(id);
        if (Objects.nonNull(user.getId())) {
            user.setPassword(userService.encodePassword(newPassword));
            var updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

}
