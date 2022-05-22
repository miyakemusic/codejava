package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.miyake.demo.entities.PortTestEntity;
@Repository
public interface PortTestRepository extends JpaRepository<PortTestEntity, Long>  {

	public List<PortTestEntity> findByPort(Long port);
	@Transactional
	public void deleteByPort(Long port);
	public List<PortTestEntity> findByTester(Long testerid);
	public List<PortTestEntity> findByTestItem(Long id);

}
