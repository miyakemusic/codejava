package com.miyake.demo.jsonobject;

import java.util.ArrayList;
import java.util.List;

public class TestItemListElement {
	private List<TestItemListSubElement> items = new ArrayList<>();
	
	public void add(Long porttest_id, Long port_direction, Long testitem_id, String testitem_name, Long myTester, List<String> candidates) {
		items.add(new TestItemListSubElement(porttest_id, port_direction, testitem_id, testitem_name, myTester, candidates));
	}

	public List<TestItemListSubElement> getItems() {
		return items;
	}

	
}
