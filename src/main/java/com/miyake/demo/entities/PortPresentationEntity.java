package com.miyake.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "port_presentation")
public class PortPresentationEntity implements Cloneable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long port;
	
	private Integer x;
	
	private Integer y;

	private Integer width;
	
	private Integer height;
	
	@Override
	public PortPresentationEntity clone() {
		try {
			PortPresentationEntity ret = (PortPresentationEntity)super.clone();
			ret.setId(null);
			return ret;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
