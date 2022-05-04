package com.miyake.demo.jsonobject;

public class PortSummaryJson {

	public Long id;
	public String name;
	public String testPoints;
	public String tests;
	
	public PortSummaryJson(Long id, String port_name, String testPoints, String testItems) {
		this.id = id;
		this.name = port_name;
		this.testPoints = testPoints;
		this.tests = testItems;
	}

}
