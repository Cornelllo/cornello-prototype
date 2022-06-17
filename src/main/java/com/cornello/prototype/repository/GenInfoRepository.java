package com.cornello.prototype.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cornello.prototype.entity.GenInfo;

public interface GenInfoRepository extends JpaRepository<GenInfo, Integer> {

    GenInfo findByGrKey(String grKey);
    
}
