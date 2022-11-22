package com.costrategix.gbp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.costrategix.gbp.entity.Invite;

@Repository
public interface InviteRepo extends JpaRepository<Invite,Integer>{
	public Optional<Invite> findByInviteToken(String token);
	public Long removeByUserId(int userId);
	public Optional<List<Invite>> findByMailStatus(int status);
}
