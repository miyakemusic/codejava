package com.miyake.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miyake.demo.entities.EquipmentCategoryEntity;

@Repository
public interface EquipmentCategoryRepository extends JpaRepository<EquipmentCategoryEntity, Long>  {

	EquipmentCategoryEntity findByCategory(String value);

}
