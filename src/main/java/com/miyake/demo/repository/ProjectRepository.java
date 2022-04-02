package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miyake.demo.entities.ProjectEntity;
@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long>  {

	List<ProjectEntity> findByUsergroup(Long id);

}
