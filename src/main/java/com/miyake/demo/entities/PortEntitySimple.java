package com.miyake.demo.entities;

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
@Table(name = "port")
public class PortEntitySimple implements Cloneable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String port_name;

	private Long connector_type;

	private Double fiber_length;
	
	private Long equipment;
    
	private Long opposite;

	@Override
	public PortEntitySimple clone() {
		
		try {
			PortEntitySimple ret = (PortEntitySimple)super.clone();
			ret.setId(null);
			ret.setOpposite(null);
			return ret;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
