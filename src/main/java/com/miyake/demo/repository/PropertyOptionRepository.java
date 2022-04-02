package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miyake.demo.entities.PropertyOptionEntity;

public interface PropertyOptionRepository extends JpaRepository<PropertyOptionEntity, Long> {

	List<PropertyOptionEntity> findByProperty(Long id);

}
