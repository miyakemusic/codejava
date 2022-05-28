package com.miyake.demo.jsonobject;

public class PortTestJson {
	
	public PortTestJson() {}

	public PortTestJson(Long id2, String port_name2, String testPoint, String testCategory, Long test_item_id, String test_item2, String criteria2,
			String result2, String passFail2) {

		this.id = id2;
		this.portName = port_name2;
		this.testPoint = testPoint;
		this.testCategory = testCategory;
		this.testItemId = test_item_id;
		this.testItem = test_item2;
		this.criteria = criteria2;
		this.result = result2;
		this.passFail = passFail2;
		
		
	}
	public Long id;
	
	public Long porttestgroup;
	public String testItem;
	public Long testItemId;
	public Long direction;
	public String result;
	public String criteria;
	public Long tester;
	public String portName;
	public String testPoint;
	public String testCategory;
	public String passFail;
}
