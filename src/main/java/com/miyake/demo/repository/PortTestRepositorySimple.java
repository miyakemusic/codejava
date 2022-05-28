package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.miyake.demo.entities.PortTestEntity;
import com.miyake.demo.entities.PortTestEntitySimple;
@Repository
public interface PortTestRepositorySimple extends JpaRepository<PortTestEntitySimple, Long>  {

//	public List<PortTestEntitySimple> findByPort(Long port);
//	@Transactional
//	public void deleteByPort(Long port);
//	public List<PortTestEntitySimple> findByTester(Long testerid);
	
	@Transactional
	public void deleteByTestItem(Long id);

}
