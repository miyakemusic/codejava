package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miyake.demo.entities.PropertyEntity;

public interface PropertyRepository  extends JpaRepository<PropertyEntity, Long>  {

	List<PropertyEntity> findByTestinstrument(Long id);

}
