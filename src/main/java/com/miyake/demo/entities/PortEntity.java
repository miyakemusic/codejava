package com.miyake.demo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Entity
@Table(name = "port")
public class PortEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String port_name;

	private Long connector_type;

	private Double fiber_length;
	
	private Long equipment;
    
	private Long opposite;
	
	@OneToMany(mappedBy="port")
	private List<PortTestEntity> portTests;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "connector_type", insertable = false, updatable = false, nullable = true)
    private ConnectorEntity connector_typeEntity;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "equipment", insertable = false, updatable = false, nullable = true)
//    private EquipmentEntity equipmentEntity;
    
    @Override
    public String toString() {
    	String ret = "";
    	List<String> in = new ArrayList<>();
    	List<String> out = new ArrayList<>();
    	
    	for (PortTestEntity p : portTests) {
    		if (p.getDirection() == 0) {
    			in.add(p.getTest_itemEntity().toString());
    		}
    		else {
    			out.add(p.getTest_itemEntity().toString());
    		}
    	}
    	
    	return "in:" + in.toString() + ", out=" + out.toString();
    }    
    
}
