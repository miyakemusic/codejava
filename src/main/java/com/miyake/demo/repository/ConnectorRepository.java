package com.miyake.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miyake.demo.entities.ConnectorEntity;
@Repository
public interface ConnectorRepository extends JpaRepository<ConnectorEntity, Long>  {

}
