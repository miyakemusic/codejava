package com.miyake.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miyake.demo.entities.TesterVendorEntity;
@Repository
public interface TesterVendorRepository extends JpaRepository<TesterVendorEntity, Long>  {

}
