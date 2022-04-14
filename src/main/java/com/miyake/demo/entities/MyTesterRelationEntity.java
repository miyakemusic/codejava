package com.miyake.demo.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Entity
@Table(name = "mytester_relation")
public class MyTesterRelationEntity {
	
	public MyTesterRelationEntity() {}
	
	public MyTesterRelationEntity(Long parent, Long child) {
		this.parent = parent;
		this.child = child;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long parent;
	
	private Long child;
}
