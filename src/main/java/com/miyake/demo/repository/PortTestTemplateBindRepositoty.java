package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miyake.demo.entities.PortTestTemplateBindEntity;

public interface PortTestTemplateBindRepositoty extends JpaRepository<PortTestTemplateBindEntity, Long>  {

	List<PortTestTemplateBindEntity> findByTemplate(Long templateid);

}
