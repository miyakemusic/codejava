package com.miyake.demo.jsonobject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Data;

@Data
public class TestPlanPort {
	private Map<Long, TestPlanPortDetail> tests = new LinkedHashMap<>();
	
	public void testItem(Long id, Long test_item, Long direction, Long tester, String result, boolean support) {
		this.tests.put(id, new TestPlanPortDetail(test_item, direction, tester, result, support));
	}

	public void sortByTester() {
		new MySorter() {
			@Override
			protected int handle(Entry<Long, TestPlanPortDetail> o1, Entry<Long, TestPlanPortDetail> o2) {
				return convertValue(o1.getValue().isSupport()) - convertValue(o2.getValue().isSupport());
			}
			private int convertValue(boolean support) {
				return support ? 0 : 1;
			}
		};
	}

	public void sortByDirection() {
		new MySorter() {
			@Override
			protected int handle(Entry<Long, TestPlanPortDetail> o1, Entry<Long, TestPlanPortDetail> o2) {
				return o1.getValue().getDirection().intValue() - o2.getValue().getDirection().intValue();
			}
		};
	}
	
	abstract class MySorter {
		abstract protected int handle(Entry<Long, TestPlanPortDetail> o1, Entry<Long, TestPlanPortDetail> o2);
		public MySorter() {
			List<Entry<Long, TestPlanPortDetail>> entries = new ArrayList<>(tests.entrySet());
			Collections.sort(entries, new Comparator<Entry<Long, TestPlanPortDetail>>() {
				@Override
				public int compare(Entry<Long, TestPlanPortDetail> o1, Entry<Long, TestPlanPortDetail> o2) {
					return handle(o1, o2);
				}
			});
			
			tests.clear();
			entries.forEach( e -> {
				tests.put(e.getKey(), e.getValue());
			});		
		}

	}
}
