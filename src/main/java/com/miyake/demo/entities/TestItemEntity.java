package com.miyake.demo.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "test_item")
public class TestItemEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String test_item;
	
	private Long category;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category", insertable = false, updatable = false)
    private TestItemCategoryEntity categoryEntity;
    
	@Override
	public String toString() {
		return "[" + categoryEntity.getCategory() + "] " + test_item;
	}
}
