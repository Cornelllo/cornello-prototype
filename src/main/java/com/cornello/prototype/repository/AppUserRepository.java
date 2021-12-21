package com.cornello.prototype.repository;

import com.cornello.prototype.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
    List<AppUser> findByFullNameAndUsername(String fullName, String username);
}
