package com.miyake.demo.jsonobject;

public class ProjectJson {
	

	

	public ProjectJson() {}
	public ProjectJson(Long id2, String name2, String comment, int total, int pass, int fail, int untested) {
		String cls = "";
		if (fail > 0) {
			cls = "text-danger";
		}
		progress = "% Total:" +  total + ", Pass:" + pass + ", Fail:<label class=\"" + cls + "\">" + fail + "</label>";

		this.id = id2;
		this.name = name2;
		this.comment = comment;
		this.total = total;
		this.pass = pass;
		this.fail = fail;
		this.untested = untested;
		if (total > 0) {
			this.percent = (pass + fail) * 100 / total;
		}
		else {
			this.percent = 0;
		}
	}
	public ProjectJson(Long id2, String name2) {
		this.id = id2;
		this.name = name2;
	}
	public Long id;
	public String name;
	public String comment;
	public String progress;
	public int total;
	public int pass;
	public int fail;
	public int untested;
	public int percent;
	
	@Override
	public String toString() {
		return this.name;
	}
}
