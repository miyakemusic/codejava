package com.miyake.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miyake.demo.entities.TestInstrumentEntity;

public interface TestInstrumentRepository extends JpaRepository<TestInstrumentEntity, Long> {

}
