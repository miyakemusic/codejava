package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miyake.demo.entities.TestItemEntity;
@Repository
public interface TestItemRepository extends JpaRepository<TestItemEntity, Long>{

	List<TestItemEntity> findByCategory(Long id);

}
