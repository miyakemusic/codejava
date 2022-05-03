package com.miyake.demo.jsonobject;

import java.util.ArrayList;
import java.util.List;

import com.miyake.demo.entities.ProductType;

public class TesterJson {
	
	public Long id;
	public String name;
	public String description;
	public List<Long> category = new ArrayList<>();
	public String categoryText;
	public Long vendorid;
	public int type;
	public String typeText;
	public String spec;
	public String parents;
	
	public TesterJson() {}
	
	public TesterJson(Long vendorid, Long id, String product_name, String description, 
			Integer type, String typeText, List<Long> category2, String categoryText, String parents) {
		this.vendorid = vendorid;
		this.id = id;
		this.name = product_name;
		this.description = description;
		this.categoryText = categoryText;
		this.category = category2;
		this.type = type;
		this.typeText = typeText;
		this.parents = parents;
	}

}
