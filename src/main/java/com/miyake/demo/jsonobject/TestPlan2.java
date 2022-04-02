package com.miyake.demo.jsonobject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class TestPlan2 {
	private List<TestPlan2Element> elements = new ArrayList<>();
	private TestPlanPresentation presentation = new TestPlanPresentation();
	private Long equipmentid = 0L;
	
	public void add(TestPlan2Element element) {
		this.elements.add(element);
	}

	public TestPlanPresentation presentation() {
		return this.presentation;
	}

	public void sortByPort() {
		Collections.sort(elements, new Comparator<TestPlan2Element>() {
			@Override
			public int compare(TestPlan2Element o1, TestPlan2Element o2) {
				return o1.getPort().intValue() - o2.getPort().intValue();
			}
		});
	}

	public void sortByTester() {
		Collections.sort(elements, new Comparator<TestPlan2Element>() {
			@Override
			public int compare(TestPlan2Element o1, TestPlan2Element o2) {
				return (o2.isMytest() ? 0:1) - (o1.isMytest() ? 0:1);
			}
		});
	}

	public void sortByDirection() {
		Collections.sort(elements, new Comparator<TestPlan2Element>() {
			@Override
			public int compare(TestPlan2Element o1, TestPlan2Element o2) {
				return o1.getDirection().compareTo(o2.getDirection());
			}
		});
	}

	public void sortByEquipment() {
		Collections.sort(elements, new Comparator<TestPlan2Element>() {
			@Override
			public int compare(TestPlan2Element o1, TestPlan2Element o2) {
				return o1.getEquipment().intValue() - o2.getEquipment().intValue();
			}
		});
	}

	public List<Long> equipments() {
		Set<Long> ret = new HashSet<>();
		this.elements.forEach(e -> {
			ret.add(e.getEquipment());
		});
		return new ArrayList<Long>( ret );
	}

	public void filterEquipment(Long id) {
		this.equipmentid = id;
	}

	public List<TestPlan2Element> filteredElements() {
		if (this.equipmentid == 0L) {
			return this.elements;
		}
		else {
			List<TestPlan2Element> ret = new ArrayList<>();
			this.elements.forEach(e -> {
				if (e.getEquipment().equals(this.equipmentid)) {
					ret.add(e);
				}
			});
			return ret;
		}
	}

	public void clearResults() {
		this.filteredElements().forEach(e -> {
			e.setResult("");
		});
	}

	public void mergeResult(TestPlan2Element testPlan2Element) {
		for (TestPlan2Element e : this.elements) {
			if (e.unique().equals(testPlan2Element.unique())) {
				e.setResult(testPlan2Element.getResult());
				break;
			}
		}
	}

	public int findIndex(TestPlan2Element testPlan2Element) {
		for (int i = 0; i < this.elements.size(); i++) {
			if (testPlan2Element.unique().equals(this.elements.get(i).unique())) {
				return i;
			}
		}
		return -1;
	}
}
