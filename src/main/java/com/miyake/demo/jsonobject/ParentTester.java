package com.miyake.demo.jsonobject;

public class ParentTester {

	public boolean selected;
	public Long id;
	public String name;
	public String description;

	public ParentTester(Long id, String product_name, String description, boolean b) {
		this.id = id;
		this.name = product_name;
		this.description = description;
		this.selected = b;
		
	}

}
