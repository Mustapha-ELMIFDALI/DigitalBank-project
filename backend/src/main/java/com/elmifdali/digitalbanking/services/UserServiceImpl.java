package com.elmifdali.digitalbanking.services;

import com.elmifdali.digitalbanking.entities.AppRole;
import com.elmifdali.digitalbanking.entities.AppUser;
import com.elmifdali.digitalbanking.repositories.AppRoleRepository;
import com.elmifdali.digitalbanking.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AppUserRepository userRepository;
    private final AppRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public AppRole saveRole(AppRole role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        AppRole role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
        user.getRoles().add(role);
    }

    @Override
    public AppUser getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    @Override
    public List<AppUser> listUsers() {
        return userRepository.findAll();
    }
}
