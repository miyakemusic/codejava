package com.miyake.demo.jsonobject;

public class ProjectJson {
	public ProjectJson() {}
	public ProjectJson(Long id2, String name2, String comment) {
		this.id = id2;
		this.name = name2;
		this.comment = comment;
	}
	public ProjectJson(Long id2, String name2) {
		this.id = id2;
		this.name = name2;
	}
	public Long id;
	public String name;
	public String comment;
	
	@Override
	public String toString() {
		return this.name;
	}
}
