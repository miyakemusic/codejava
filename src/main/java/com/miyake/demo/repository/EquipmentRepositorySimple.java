package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miyake.demo.entities.EquipmentEntity;
import com.miyake.demo.entities.EquipmentEntitySimple;

@Repository
public interface EquipmentRepositorySimple extends JpaRepository<EquipmentEntitySimple, Long>  {

	List<EquipmentEntitySimple> findByProject(Long project);

	List<EquipmentEntitySimple>  findByStatus(Integer status);

}
