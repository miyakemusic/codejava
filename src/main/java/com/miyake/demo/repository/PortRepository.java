package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.miyake.demo.entities.PortEntity;

@Repository
public interface PortRepository extends JpaRepository<PortEntity, Long>  {
//    @Query("SELECT u FROM port u WHERE u.equipment = ?1")
    public List<PortEntity> findByEquipment(Long equipment);

	public List<PortEntity> findByOpposite(Long port1);

	@Transactional
	public void deleteByEquipment(Long id);
}
