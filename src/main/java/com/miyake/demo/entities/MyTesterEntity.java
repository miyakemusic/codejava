package com.miyake.demo.entities;

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
@Table(name = "mytester")
public class MyTesterEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long tester;
	
	private String name;

	private Long usergroup;
	
	private Boolean online;
	
	private Long parent;
	
	private String password;
	@Override
	public String toString() {
		return name;
	}
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usergroup", insertable = false, updatable = false, nullable = true)
    private UserGroupEntity usergroupEntity;
    
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "tester", insertable = false, updatable = false, nullable = true)
//    private TesterEntity testerEntity;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "tester", insertable = false, updatable = false)
//    private TesterEntity testerEntity;
}
