package com.miyake.demo.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Entity
@Table(name = "port_test_group")
public class PortTestGroupEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private Long port;
	
	@OneToMany(mappedBy="porttestgroup")
	private List<PortTestEntity> portTests;
	
	@Override
	public PortTestGroupEntity clone() {
		try {
			PortTestGroupEntity ret = (PortTestGroupEntity)super.clone();
			ret.id = null;
			return ret;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
