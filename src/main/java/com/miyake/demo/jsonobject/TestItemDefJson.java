package com.miyake.demo.jsonobject;

public class TestItemDefJson {

	public Long id;
	public String category;
	public String testitem;
	public String description;

	public TestItemDefJson() {}
	
	public TestItemDefJson(Long id, String category, String test_item, String description) {
		this.id = id;
		this.category = category;
		this.testitem = test_item;
		this.description = description;
		if (this.description == null) {
			this.description = "--";
		}
	}

}
