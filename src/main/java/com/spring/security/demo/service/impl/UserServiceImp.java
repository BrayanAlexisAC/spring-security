package com.spring.security.demo.service.impl;

import com.spring.security.demo.model.SSUser;
import com.spring.security.demo.repository.UserRepository;
import com.spring.security.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<SSUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public SSUser getUserById(Long id)  {
        return userRepository.findById(id).orElse(new SSUser());
    }

    @Override
    public SSUser createUser(SSUser user) {
        return userRepository.save(user);
    }

    @Override
    public SSUser updateUser(Long id, SSUser user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    user.setId(existingUser.getId());
                    return userRepository.save(user);
                }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
