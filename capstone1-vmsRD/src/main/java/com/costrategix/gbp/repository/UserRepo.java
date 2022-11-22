package com.costrategix.gbp.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.costrategix.gbp.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User,Integer>{


	public List<User> findUserByOrgid(int orgid);

	Optional<User> findByEmailAdress(String email);
	

	
}
