package com.costrategix.gbp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.costrategix.gbp.common.OrganizationLocations;
import com.costrategix.gbp.entity.Organizations;
import com.costrategix.gbp.entity.location_group;
import com.costrategix.gbp.entity.subLocations;
import com.costrategix.gbp.repository.OrganizationRepo;
import com.costrategix.gbp.repository.location_groupRepo;
import com.costrategix.gbp.repository.subLocationsRepo;

@Service
public class LocationService {

	@Autowired
	private subLocationsRepo subLocationRepo;
	
	@Autowired
	private OrganizationRepo orgRepo;
	
	@Autowired
	private location_groupRepo groupRepo;
	
	private static final Logger LOGGER = LogManager.getLogger(LocationService.class);

	
	@Cacheable(key= "#orgId", value = "Locations")
	public List<OrganizationLocations> getAll(int orgId){
		Map<location_group,List<subLocations>> map = new HashMap<>();
		Organizations org = orgRepo.findById(orgId).get();
		for (subLocations sub : org.getSublocations())
		{
			location_group lg = sub.getLg();
			if(map.containsKey(lg)) {
				map.get(lg).add(sub);
			}
			else {
				List<subLocations> L = new ArrayList<>();
				L.add(sub);
				map.put(lg,L);
			}
		}
		List<OrganizationLocations> ls = new ArrayList<>();
		
		for(Map.Entry<location_group,List<subLocations>> entry: map.entrySet()) {
			ls.add(new OrganizationLocations(entry.getKey().getLoc_id(),entry.getKey().getStatename(),entry.getValue()));
		}
		return ls;
	}
	

	@Cacheable(key= "#orgId", value = "GroupLocations")
	public Set<location_group> getlocationgroups(int orgId)
	{
		Set<location_group> sublocation = new HashSet<>();
		Organizations org = orgRepo.findById(orgId).get();
		for (subLocations sub : org.getSublocations())
		{
			sublocation.add(sub.getLg());
		}
		return  sublocation;
	}
	
	@Cacheable(key= "#orgId", value = "SubLocations")
	public List<subLocations> getsublocation(int orgId,int grpLocId)

	{
		Set<location_group> sublocation = new HashSet<>();
		Organizations org = orgRepo.findById(orgId).get();
		List<subLocations> subList = new ArrayList<>();
		location_group lg = groupRepo.findById(grpLocId).get();
		
		List<subLocations> sub = subLocationRepo.findByLg(lg);
		for (subLocations subs : org.getSublocations())
		{
			for (subLocations s : sub) { 
				if (subs.getSub_loc_id() == s.getSub_loc_id()) {
					subList.add(s);
				}
			}
		}
	
		return subList;
	}
}
