package com.miyake.demo.jsonobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.miyake.demo.entities.MyTesterEntity;

public class TestItemList {

	private Map<Long, TestItemListElement> map = new LinkedHashMap<>();
	private Map<Long, String> portNames = new HashMap<>();
	private List<MyTesterJson> myTesters = new ArrayList<>();
	
	public TestItemListElement createPort(Long id, String port_name) {
		TestItemListElement item = new TestItemListElement();
		map.put(id, item);
		portNames.put(id, port_name);
		return item;
	}

	public Map<Long, TestItemListElement> getMap() {
		return map;
	}

	public Map<Long, String> getPortNames() {
		return portNames;
	}

	public List<MyTesterJson> getMyTesters() {
		return myTesters;
	}

	public List<List<String>> convertToTable() {
		List<List<String>> ret = new ArrayList<>();
		
		for (Map.Entry<Long, TestItemListElement> entry : map.entrySet()) {
			for (TestItemListSubElement item : entry.getValue().getItems()) {
				List<String> line = new ArrayList<>();
				ret.add(line);
				line.add(portNames.get(entry.getKey()));	
				line.add(item.port_direction == 0 ? "Inside": "Outside");
				line.add(item.testitem_name);
				line.add(myTesterName(item.myTester));
				line.add(item.candidates.toString());
			}
		}
		return ret;
	}

	private String myTesterName(Long myTester) {
		for (MyTesterJson t : myTesters) {
			if (t.getId().equals(myTester)) {
				return t.getMyTesterName();
			}
		}
		return "";
	}

	public void addMyTesters(List<MyTesterEntity> testers, Map<Long, String> map) {
		for (MyTesterEntity tester : testers) {
			MyTesterJson json = new MyTesterJson(tester.getId(), tester.getName(), map.get(tester.getTester()));
			myTesters.add(json);
		}
	}

}
