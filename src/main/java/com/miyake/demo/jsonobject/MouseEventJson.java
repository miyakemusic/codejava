package com.miyake.demo.jsonobject;

public class MouseEventJson {
	public int x;
	public int y;
	public MyMouseEvent event;
	public String name;
	
	public enum MyMouseEvent {
		MousePress,
		MouseRelease,
		MouseClick,
		MouseMove,
	}
	public MouseEventJson() {}
	public MouseEventJson(String name, int x, int y, MyMouseEvent event) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.event = event;
	}
}
