package com.miyake.demo.jsonobject;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

public class TestPlan {
	private Map<Long, TestPlanEquipment> equipments = new HashMap<>();
	private TestPlanPresentation presentation = new TestPlanPresentation();
	
	public TestPlanEquipment equipment(Long id) {
		if (!equipments.containsKey(id)) {
			equipments.put(id, new TestPlanEquipment());
		}
		return this.equipments.get(id);
	}

	public TestPlanPresentation presentation() {
		return presentation;
	}
	
	@Override
	public String toString() {
		String ret = "";
		for (Map.Entry<Long, TestPlanEquipment> equipment : equipments.entrySet()) {
			ret += this.presentation.equipment(equipment.getKey()) + "\n";
			for (Map.Entry<Long, TestPlanPort> port : equipment.getValue().getPorts().entrySet()) {
				ret += "  " + this.presentation().port(port.getKey()) + "\n";
				for (Map.Entry<Long, TestPlanPortDetail> testItem : port.getValue().getTests().entrySet()) {
					if (testItem.getValue().isSupport()) {
						ret += "(**)";
					}
					else {
						ret += "(--)";
					}
					ret += /*"    " +*/ this.presentation().testItem(testItem.getKey()) + "\n";
				}
			}
		}
		
		return ret;
	}

	public void sortByPort(Long equipmentid) {
		this.equipments.get(equipmentid).sortByPort();
	}

	public void sortByTester(Long equipmentid) {
		this.equipments.get(equipmentid).getPorts().forEach((id, tp) -> {
			tp.sortByTester();
		});
	}

	public void sortByDirection(Long equipmentid) {
		this.equipments.get(equipmentid).getPorts().forEach((id, tp) -> {
			tp.sortByDirection();
		});
	}
}
