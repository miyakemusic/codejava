package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miyake.demo.entities.UserEntity;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
//    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public UserEntity findByEmail(String email);

	public List<UserEntity> findByUsergroup(Long usergroup);
}