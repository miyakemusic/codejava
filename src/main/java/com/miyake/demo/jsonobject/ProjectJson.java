package com.miyake.demo.jsonobject;

public class ProjectJson {
	public ProjectJson() {}
	public ProjectJson(Long id2, String name2, String comment, String progress, int pass, int fail, int untested) {
		this.id = id2;
		this.name = name2;
		this.comment = comment;
		this.progress = progress;
		this.pass = pass;
		this.fail = fail;
		this.untested = untested;
	}
	public ProjectJson(Long id2, String name2) {
		this.id = id2;
		this.name = name2;
	}
	public Long id;
	public String name;
	public String comment;
	public String progress;
	public int pass;
	public int fail;
	public int untested;
	
	@Override
	public String toString() {
		return this.name;
	}
}
