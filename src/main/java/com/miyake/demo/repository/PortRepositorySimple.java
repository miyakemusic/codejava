package com.miyake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miyake.demo.entities.PortEntity;
import com.miyake.demo.entities.PortEntitySimple;

@Repository
public interface PortRepositorySimple extends JpaRepository<PortEntitySimple, Long>  {
//    @Query("SELECT u FROM port u WHERE u.equipment = ?1")
    public List<PortEntitySimple> findByEquipment(Long equipment);

	public List<PortEntitySimple> findByOpposite(Long port1);
}
