package com.miyake.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.miyake.demo.entities.EquipmentPresentationEntity;

public interface EquipmentPresentationRepository extends JpaRepository<EquipmentPresentationEntity, Long>{

	EquipmentPresentationEntity findByEquipment(Long id);

	@Transactional
	void deleteByEquipment(Long id);

}
