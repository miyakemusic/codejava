package com.miyake.demo.jsonobject;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class TestPlanPresentation {

	private Map<Long, String> equipment = new HashMap<>();
	private Map<Long, String> port = new HashMap<>();
	private Map<Long, String> testItem = new HashMap<>();
	
	public TestPlanPresentation equipment(Long id, String name) {
		this.equipment.put(id, name);
		return this;
	}

	public TestPlanPresentation port(Long port2, String port_name) {
		this.port.put(port2, port_name);
		return this;
	}

	public TestPlanPresentation testItem(Long test_item, String item) {
		this.testItem.put(test_item, item);
		return this;
	}

	public String equipment(Long id) {
		return this.equipment.get(id);
	}

	public String port(Long id) {
		return this.port.get(id);
	}

	public String testItem(Long id) {
		return this.testItem.get(id);
	}

}
