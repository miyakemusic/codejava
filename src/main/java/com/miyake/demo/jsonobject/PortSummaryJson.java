package com.miyake.demo.jsonobject;

public class PortSummaryJson {

	public Long id;
	public String name;
	public String testPoints;
	public String tests;
	public String linkEquipment;
	public String linkPort;
	
	public PortSummaryJson(Long id, String port_name,  String linkEquipment, String linkPort, String testPoints, String testItems) {
		this.id = id;
		this.name = port_name;
		this.linkEquipment = linkEquipment;
		this.linkPort = linkPort;
		this.testPoints = testPoints;
		this.tests = testItems;
	}

}
