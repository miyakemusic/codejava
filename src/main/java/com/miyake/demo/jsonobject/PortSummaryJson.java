package com.miyake.demo.jsonobject;

public class PortSummaryJson {

	public Long id;
	public String name;
	public String testPoints;
	public String tests;
	public String linkEquipment;
	public String linkPort;
	public String cable;
	public String status;
	
	public PortSummaryJson(Long id, String port_name,  String linkEquipment, String linkPort, String cable, String testPoints, String testItems, String status) {
		this.id = id;
		this.name = port_name;
		this.linkEquipment = linkEquipment;
		this.linkPort = linkPort;
		this.cable = cable;
		this.testPoints = testPoints;
		this.tests = testItems;
		this.status = status;
	}

}
