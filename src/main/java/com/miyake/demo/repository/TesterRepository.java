package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miyake.demo.entities.TesterEntity;
@Repository
public interface TesterRepository extends JpaRepository<TesterEntity, Long>{

	List<TesterEntity> findByVendor(Long vendor);

}
