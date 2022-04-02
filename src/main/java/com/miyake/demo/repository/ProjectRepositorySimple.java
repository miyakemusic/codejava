package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miyake.demo.entities.ProjectEntitySimple;
@Repository
public interface ProjectRepositorySimple extends JpaRepository<ProjectEntitySimple, Long>  {

	List<ProjectEntitySimple> findByUsergroup(Long id);

}
