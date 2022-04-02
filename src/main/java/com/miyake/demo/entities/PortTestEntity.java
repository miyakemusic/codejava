package com.miyake.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Entity
@Table(name = "port_test")
public class PortTestEntity implements Cloneable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long port;
	
	@Transient
	public String port_name = "";
	
	@Column(name = "test_item")
	private Long testItem;

	private Long direction;
	
	private String result;
	
	private String criteria;
	
	private Long tester;

	@Enumerated(EnumType.ORDINAL)
	private PassFailEnum passfail = PassFailEnum.Untested;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "direction", insertable = false, updatable = false, nullable = true)
    private PortDirectionEntity directionEntity;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "test_item", insertable = false, updatable = false, nullable = true)
    private TestItemEntity test_itemEntity;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tester", insertable = false, updatable = false, nullable = true)
    private MyTesterEntity testerEntity;
    
	@Override
	public PortTestEntity clone(){
		try {
			PortTestEntity ret = (PortTestEntity)super.clone();
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
