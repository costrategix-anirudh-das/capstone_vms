package com.costrategix.gbp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.costrategix.gbp.entity.Roles;



@Repository
public interface RoleRepo extends JpaRepository<Roles,Integer> {
   
	List<Roles> findRolesByOrganizationsId(Integer orgid);
}
