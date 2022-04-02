package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miyake.demo.entities.MyTesterEntity;

public interface MyTesterRepository extends JpaRepository<MyTesterEntity, Long>  {

	MyTesterEntity findByName(String name);

	List<MyTesterEntity> getByUsergroup(Long userGroup);

	List<MyTesterEntity> findByTester(Long testerid);

}
