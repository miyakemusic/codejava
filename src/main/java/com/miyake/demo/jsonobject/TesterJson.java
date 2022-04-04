package com.miyake.demo.jsonobject;

import java.util.ArrayList;
import java.util.List;

public class TesterJson {
	
	public Long id;
	public String name;
	public List<Long> category = new ArrayList<>();
	public String categoryText;
	public Long vendorid;
	
	public TesterJson() {}
	
	public TesterJson(Long id, String product_name, List<Long> category2, String categoryText) {
		this.id = id;
		this.name = product_name;
		this.categoryText = categoryText;
		this.category = category2;
	}

}
