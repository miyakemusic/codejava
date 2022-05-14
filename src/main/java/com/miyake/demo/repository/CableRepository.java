package com.miyake.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miyake.demo.entities.CableEntity;
@Repository
public interface CableRepository extends JpaRepository<CableEntity, Long>  {

}
