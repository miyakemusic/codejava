package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.miyake.demo.entities.PortTestEntity;
import com.miyake.demo.entities.PortTestGroupEntity;
@Repository
public interface PortTestGroupRepository extends JpaRepository<PortTestGroupEntity, Long>  {

	List<PortTestGroupEntity> findByPort(Long id);


}
