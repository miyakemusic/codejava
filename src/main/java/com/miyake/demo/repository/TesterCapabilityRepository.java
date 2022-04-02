package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.miyake.demo.entities.TesterCapabilityEntity;

@Repository
public interface TesterCapabilityRepository extends JpaRepository<TesterCapabilityEntity, Long>  {
	@Transactional
	void deleteByTester(Long tester);

	List<TesterCapabilityEntity> findByTester(Long tester);
	@Transactional
	void deleteByTestItem(Long id);

	List<TesterCapabilityEntity> findByTestItem(Long id);

}
