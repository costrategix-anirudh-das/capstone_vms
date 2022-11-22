package com.costrategix.gbp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.costrategix.gbp.entity.Organizations;


@Repository
public interface  OrganizationRepo extends JpaRepository<Organizations,Integer>{

	List<Organizations> findOrganizationsByRolesId(Integer roleId);

	
}
