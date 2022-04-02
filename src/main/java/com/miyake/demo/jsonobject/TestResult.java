package com.miyake.demo.jsonobject;

import lombok.Data;

@Data
public class TestResult {
	private Long testitem;
	private String value;
	private Long porttest;
	
	public TestResult() {}
	public TestResult(Long testitem, String value) {
		this.testitem = testitem;
		this.value = value;
	}
}
