package com.miyake.demo.jsonobject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class TestPlanEquipment {
	private Map<Long, TestPlanPort> ports = new LinkedHashMap<>();
	
	public TestPlanPort port(Long port) {
		if (!ports.containsKey(port)) {
			ports.put(port, new TestPlanPort());
		}
		return this.ports.get(port);
	}

	public void sortByPort() {
		List<Long> ids = new ArrayList<Long>(ports.keySet());
		
		Collections.sort(ids);
		
		Map<Long, TestPlanPort> tmpports = new LinkedHashMap<>();
		ids.forEach(id -> {
			tmpports.put(id, ports.get(id));
		});
		
		this.ports = tmpports;
	}
	
}
