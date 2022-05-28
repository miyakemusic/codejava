package com.miyake.demo.jsonobject;

public class TestItemJson {
	public Long id;
	public String equipment;
	public String port;
	public String testPoint;
	public String testItem;
	public String criteria;
	public String result;
	public String passFail;
	public String testCategory;
	public String testGroup;
	
	public TestItemJson() {}

	public TestItemJson project(Long id, String equipment, String port, String testPoint, String category, String testItem, String criteria, String result, String passFail) {
		this.id = id;
		this.equipment = equipment;
		this.port = port;
		this.testPoint = testPoint;
		this.testCategory = category;
		this.testItem = testItem;
		this.criteria = criteria;
		this.result = result;
		this.passFail = passFail;
		
		return this;
	}
	
	public TestItemJson equipment(Long id, String port, String testPoint, String category, String testItem, String criteria, String result, String passFail) {
		this.id = id;
		this.port = port;
		this.testPoint = testPoint;
		this.testCategory = category;
		this.testItem = testItem;
		this.criteria = criteria;
		this.result = result;
		this.passFail = passFail;
		
		return this;
	}
	
	public TestItemJson port(Long id, String testPoint, String group, String category, String testItem, String criteria, String result, String passFail) {
		this.id = id;
		this.testPoint = testPoint;
		this.testCategory = category;
		this.testItem = testItem;
		this.criteria = criteria;
		this.result = result;
		this.passFail = passFail;
		this.testGroup = group;
		return this;
	}
}
