package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miyake.demo.entities.EquipmentEntity;

@Repository
public interface EquipmentRepository extends JpaRepository<EquipmentEntity, Long>  {

	List<EquipmentEntity> findByProject(Long project);

	List<EquipmentEntity>  findByStatus(Integer status);

}
