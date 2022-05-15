package com.miyake.demo.jsonobject;

public class TestItemJson {
	public Long id;
	public String port;
	public String testPoint;
	public String testItem;
	public String criteria;
	public String result;
	public String passFail;
	
	public TestItemJson() {}
	
	public TestItemJson(Long id, String port, String testPoint, String testItem, String criteria, String result, String passFail) {
		this.id = id;
		this.port = port;
		this.testPoint = testPoint;
		this.testItem = testItem;
		this.criteria = criteria;
		this.result = result;
		this.passFail = passFail;
	}
	
	public TestItemJson(Long id, String testPoint, String testItem, String criteria, String result, String passFail) {
		this.id = id;
		this.testPoint = testPoint;
		this.testItem = testItem;
		this.criteria = criteria;
		this.result = result;
		this.passFail = passFail;
	}
}
