package com.costrategix.gbp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.costrategix.gbp.entity.UserAccountStatus;

@Repository
public interface UserAccountRepo extends JpaRepository<UserAccountStatus,Integer>{

}
