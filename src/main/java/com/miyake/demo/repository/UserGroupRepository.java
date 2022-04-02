package com.miyake.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miyake.demo.entities.UserGroupEntity;
@Repository
public interface UserGroupRepository extends JpaRepository<UserGroupEntity, Long> {

}
