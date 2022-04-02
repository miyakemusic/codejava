package com.miyake.demo.jsonobject;

import lombok.Data;

@Data
public class TestPlanPortDetail {

	private Long direction;
	private boolean support;
	private Long tester;
	private Long testitem;

	public TestPlanPortDetail(Long test_item, Long direction2, Long tester2, String result, boolean support2) {}
	
	public TestPlanPortDetail(Long testitem, Long direction, Long tester, boolean support) {
		this.testitem = testitem;
		this.direction = direction;
		this.tester = tester;
		this.support = support;
	}

}
