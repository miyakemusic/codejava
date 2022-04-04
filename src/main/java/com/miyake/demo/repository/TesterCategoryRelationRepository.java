package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.miyake.demo.entities.TesterCategoryRelationEntity;

public interface TesterCategoryRelationRepository extends JpaRepository<TesterCategoryRelationEntity, Long>  {

	List<TesterCategoryRelationEntity> findByTester(Long tester);

	@Transactional
	void deleteByTester(Long id);
	
}
