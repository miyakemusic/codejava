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
@Table(name = "equipment_presentation")
public class EquipmentPresentationEntity implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long equipment;
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;
	
    @Override
	public EquipmentPresentationEntity clone() {
		try {
			EquipmentPresentationEntity ret = (EquipmentPresentationEntity)super.clone();
			ret.setId(null);
			return ret;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    
    
}
