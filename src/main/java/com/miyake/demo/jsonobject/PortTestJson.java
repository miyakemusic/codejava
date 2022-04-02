package com.miyake.demo.jsonobject;

public class PortTestJson {
	public PortTestJson() {}
	public PortTestJson(Long id, Long direction2, Long test_item2, String criteria) {
		this.id = id;
		this.direction = direction2;
		this.test_item = test_item2;
	}
	public Long direction;
	public Long test_item;
	public Long id;
	public String criteria;
	public String result;
	
}
