package com.miyake.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Entity
@Table(name = "tester_capability")
public class TesterCapabilityEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long tester;
	
	@Column(name = "test_item")
	private Long testItem;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "test_item", insertable = false, updatable = false, nullable = true)
    private TestItemEntity test_itemEntity;
    
    @Override
    public String toString() {
    	return this.test_itemEntity.getTest_item();
    }
}
