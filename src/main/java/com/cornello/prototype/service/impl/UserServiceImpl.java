package com.cornello.prototype.service.impl;

import com.cornello.prototype.entity.AppUser;
import com.cornello.prototype.entity.Role;
import com.cornello.prototype.repository.AppUserRepository;
import com.cornello.prototype.repository.RoleRepository;
import com.cornello.prototype.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found from database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(role ->
            authorities.add(new SimpleGrantedAuthority(role.getName()))
        );
        return new User(appUser.getUsername(),appUser.getPassword(),authorities);
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        log.info("Saving user to database");
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving role to database");
        return roleRepository.save(role);
    }

    @Override
    public void assignRole(String username, String roleName) {
        log.info("Adding role to app user");
        AppUser appUser = appUserRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        appUser.getRoles().add(role);
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Getting user from the database");
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Getting all users from the database");
        return appUserRepository.findAll();
    }

}
