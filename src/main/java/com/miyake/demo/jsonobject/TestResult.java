package com.miyake.demo.jsonobject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class TestResult {
	private Long id;
	private String value;
	
	public TestResult() {}
	public TestResult(Long id, String value) {
		this.id = id;
		this.value = value;
	}
}
