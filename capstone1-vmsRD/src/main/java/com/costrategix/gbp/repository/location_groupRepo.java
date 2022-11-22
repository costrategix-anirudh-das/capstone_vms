package com.costrategix.gbp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.costrategix.gbp.entity.location_group;


@Repository
public interface location_groupRepo extends JpaRepository<location_group,Integer>{

}
