package com.costrategix.gbp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.costrategix.gbp.entity.PasswordResetRequest;

@Repository
public interface PasswordResetRepo extends JpaRepository<PasswordResetRequest,Integer>{

	public Optional<PasswordResetRequest> findByResetToken(String token);
	
	public Long removeByUserId(int userId);
}
