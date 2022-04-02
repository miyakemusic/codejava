package com.miyake.demo.jsonobject;

public class DiagramItem {

	public DiagramItem() {}
	
	public DiagramItem(Long id2, String name, Integer x2, Integer y2, Integer width2, Integer height2) {
		this.id = id2;
		this.text = name;
		this.x = x2;
		this.y = y2;
		this.width = width2;
		this.height = height2;
		
	}
	public Long id;
	public Integer x;
	public Integer y;
	public Integer width;
	public Integer height;
	public String text;
}
