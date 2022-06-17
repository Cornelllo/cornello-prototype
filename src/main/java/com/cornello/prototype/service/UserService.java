package com.cornello.prototype.service;

import com.cornello.prototype.entity.AppUser;
import com.cornello.prototype.entity.Role;
import com.cornello.prototype.model.RequestBodyTest;

import java.util.List;

import javax.validation.Valid;

public interface UserService {
    AppUser saveUser(AppUser appUser);
    Role saveRole(Role role);
    void assignRole(String username, String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();
	List<AppUser> getUserByfullNameAndUsername(String fullName, String username)  throws Exception;
    List<AppUser> findByFullName(String fullName);
    Object testFunc(@Valid RequestBodyTest requestBody)  throws Exception;
    
}
