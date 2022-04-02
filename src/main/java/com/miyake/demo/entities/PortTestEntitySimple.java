package com.miyake.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Entity
@Table(name = "port_test")
public class PortTestEntitySimple implements Cloneable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long port;
	
	@Column(name = "test_item")
	private Long testItem;

	private Long direction;
	
	private String result;
	
	private String criteria;
	
	private Long tester;

	@Enumerated(EnumType.ORDINAL)
	private PassFailEnum passfail = PassFailEnum.Untested;
	
	@Override
	public PortTestEntitySimple clone() {

		try {
			PortTestEntitySimple ret= (PortTestEntitySimple)super.clone();
			ret.setId(null);
			ret.setResult(null);
			ret.setPort(null);
			return ret;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
