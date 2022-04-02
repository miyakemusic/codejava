package com.miyake.demo.jsonobject;

import com.miyake.demo.entities.PassFailEnum;

import lombok.Data;

@Data
public class TestPlan2Element {

	public TestPlan2Element() {}
	public TestPlan2Element(Long porttest, Long equipment, Long port, String direction, Long testitem, 
			String criteria, Long tester, String result, PassFailEnum passFail, boolean mytest) {
		
		super();
		this.porttest = porttest;
		this.equipment = equipment;
		this.port = port;
		this.direction = direction;
		this.testitem = testitem;
		this.tester = tester;
		this.mytest = mytest;
		this.result = result;
		this.criteria = criteria;
		this.passFail = passFail;
	}
	
	private Long porttest;
	private Long equipment;
	private Long port;
	private String direction;
	private Long testitem;
	private Long tester;
	private boolean mytest;
	private String result;
	private String criteria;
	private PassFailEnum passFail;
	
	public String unique() {
		return this.equipment + "." + this.port + "." + this.direction + "." + this.testitem;
	}
	public String group() {
		return this.equipment + "." + this.port + "." + this.direction;
	}
}
