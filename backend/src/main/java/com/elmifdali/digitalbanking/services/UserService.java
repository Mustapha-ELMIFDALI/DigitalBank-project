package com.elmifdali.digitalbanking.services;

import com.elmifdali.digitalbanking.entities.AppRole;
import com.elmifdali.digitalbanking.entities.AppUser;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser user);
    AppRole saveRole(AppRole role);
    void addRoleToUser(String username, String roleName);
    AppUser getUserByUsername(String username);
    List<AppUser> listUsers();
}
