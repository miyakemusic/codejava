package com.miyake.demo.jsonobject;

public class ProjectSummaryJson {

	public Long id;
	public String category;
	public String name;
	public String address;
	public String testStatus;
	public String ports;
	
	public ProjectSummaryJson() {}
	public ProjectSummaryJson(Long id, String category, String name, String address, String ports, String testStatus) {
		this.id = id;
		this.category = category;
		this.name = name;
		this.address = address;
		this.ports = ports;
		this.testStatus = testStatus;
	}

}
