package com.miyake.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miyake.demo.entities.PortCategoryEntity;

@Repository
public interface PortCategoryRepository extends JpaRepository<PortCategoryEntity, Long>  {

	PortCategoryEntity findByCategory(String value);

}
