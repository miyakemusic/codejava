package com.miyake.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miyake.demo.entities.TestScenarioEntity;

public interface TestScenarioRepository  extends JpaRepository<TestScenarioEntity, Long>{

}
