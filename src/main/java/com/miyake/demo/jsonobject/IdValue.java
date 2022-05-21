package com.miyake.demo.jsonobject;

public class IdValue {
	public Long id;
	public String name;
	
	public IdValue() {}
	
	public IdValue(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public IdValue(int id, String name) {
		this.id = Long.valueOf(id);
		this.name = name;
	}
}
