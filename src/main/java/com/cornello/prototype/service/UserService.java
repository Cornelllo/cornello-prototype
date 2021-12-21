package com.cornello.prototype.service;

import com.cornello.prototype.entity.AppUser;
import com.cornello.prototype.entity.Role;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser appUser);
    Role saveRole(Role role);
    void assignRole(String username, String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();
	List<AppUser> getUserByfullNameAndUsername(String fullName, String username);
    
}
