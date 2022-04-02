package com.miyake.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miyake.demo.entities.PortTestTemplateEntity;

public interface PortTestTemplateRepositoty extends JpaRepository<PortTestTemplateEntity, Long>  {

}
