package com.miyake.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.miyake.demo.entities.PortPresentationEntity;

public interface PortPresentationRepository extends JpaRepository<PortPresentationEntity, Long>{

	PortPresentationEntity findByPort(Long port);

	@Transactional
	void deleteByPort(Long id);

}
