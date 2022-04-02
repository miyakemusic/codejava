package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miyake.demo.entities.TestScenarioItemEntity;

public interface TestScenarioItemRepository  extends JpaRepository<TestScenarioItemEntity, Long>{

	List<TestScenarioItemEntity> findByTestScenario(Long scenario);

}
