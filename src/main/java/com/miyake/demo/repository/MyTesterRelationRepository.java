package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.miyake.demo.entities.MyTesterRelationEntity;
import com.miyake.demo.entities.TesterOptionRelationEntity;
@Repository
public interface MyTesterRelationRepository extends JpaRepository<MyTesterRelationEntity, Long>  {

	List<TesterOptionRelationEntity> findByChild(Long id);

	List<TesterOptionRelationEntity> findByParent(Long id);

	@Transactional
	void deleteByChild(Long id);

}
