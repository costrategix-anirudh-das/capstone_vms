package com.costrategix.gbp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.costrategix.gbp.entity.EmailTemplate;

@Repository
public interface EmailTemplateRepo extends JpaRepository<EmailTemplate,Integer>{
	
}
