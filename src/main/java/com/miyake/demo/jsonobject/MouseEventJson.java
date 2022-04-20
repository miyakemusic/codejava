package com.miyake.demo.jsonobject;

public class MouseEventJson {
	public int x;
	public int y;
	public MouseEvent event;
	public String name;
	
	enum MouseEvent {
		MouseDown,
		MouseUp,
		MouseClick
	}
	public MouseEventJson() {}
	public MouseEventJson(String name, int x, int y, MouseEvent event) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.event = event;
	}
}
