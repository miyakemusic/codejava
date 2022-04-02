package com.miyake.demo.jsonobject;

import java.util.List;

public class TestItemListSubElement {
	
	public TestItemListSubElement() {}
	public TestItemListSubElement(Long porttest_id, Long port_direction, Long testitem_id, String testitem_name, 
			Long myTester,List<String> candidates) {
		this.porttest_id = porttest_id;
		this.testitem_id = testitem_id;
		this.testitem_name = testitem_name;
		this.candidates = candidates;
		this.port_direction = port_direction;
		this.myTester = myTester;
	}
	
	public Long porttest_id;
	public Long testitem_id;
	public String testitem_name;
	public List<String> candidates;
	public Long port_direction;
	public Long myTester;
}