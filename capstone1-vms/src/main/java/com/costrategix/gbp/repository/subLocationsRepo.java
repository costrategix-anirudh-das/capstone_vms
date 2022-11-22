package com.costrategix.gbp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.costrategix.gbp.entity.location_group;
import com.costrategix.gbp.entity.subLocations;

@Repository
public interface subLocationsRepo extends JpaRepository<subLocations,Integer>{
	
	List<subLocations> findByLg(location_group lg);
}
