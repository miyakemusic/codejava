package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.miyake.demo.entities.TesterCapabilityEntity;
import com.miyake.demo.entities.TesterCapabilityEntitySimple;

@Repository
public interface TesterCapabilityRepositorySimple extends JpaRepository<TesterCapabilityEntitySimple, Long>  {
	@Transactional
	void deleteByTester(Long tester);

	List<TesterCapabilityEntitySimple> findByTester(Long tester);

	@Transactional
	void deleteByTestItem(Long id);

	List<TesterCapabilityEntitySimple> findByTestItem(Long id);

}
