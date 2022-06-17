package com.cornello.prototype.repository;

import com.cornello.prototype.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
    List<AppUser> findByFullNameAndUsername(String fullName, String username) throws Exception;

    //Stored procedures
    @Procedure(procedureName = "findByFullName")
    List<AppUser> findByFullName(String fullName);
}
