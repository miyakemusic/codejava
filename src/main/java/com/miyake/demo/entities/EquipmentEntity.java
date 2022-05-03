package com.miyake.demo.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Entity
@Table(name = "equipment")
public class EquipmentEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long category;
	
	private String name;
	
	private Long project;
	
	private Integer status;
	
	private String address;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category", insertable = false, updatable = false, nullable=true)
    private EquipmentCategoryEntity categoryEntity;

	@OneToMany(mappedBy="equipment")
	private List<PortEntity> ports;
	
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "project", insertable = false, updatable = false, nullable=true)
//    private ProjectEntity projectEntity;
    
    
    @Override
    public String toString() {
    	try {
    		return categoryEntity.getCategory() + ":" + name;
    	}
    	catch (Exception e) {
//    		e.printStackTrace();
    	}
    	return "";
    }
    
}
