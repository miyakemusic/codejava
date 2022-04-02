package com.miyake.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miyake.demo.entities.TestItemCategoryEntity;
@Repository
public interface TestItemCategoryRepository extends JpaRepository<TestItemCategoryEntity, Long>{

}
