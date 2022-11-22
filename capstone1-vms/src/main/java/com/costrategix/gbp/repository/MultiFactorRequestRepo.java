package com.costrategix.gbp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.costrategix.gbp.entity.MultiFactorRequest;

@Repository
public interface MultiFactorRequestRepo extends JpaRepository<MultiFactorRequest,Integer>{
	
	Optional<MultiFactorRequest> findByUserId(int userId);
}
