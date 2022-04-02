package com.miyake.demo.jsonobject;

import lombok.Data;

@Data
public class MyTesterJson {

	private Long id;
	private String category;
	private String vendor;
	private String myTesterName;
	private String testerName;
	private String status;
	
	public MyTesterJson() {}
	public MyTesterJson(Long id, String myTesterName, String testerName) {
		this.id = id;
		this.myTesterName = myTesterName;
		this.testerName = testerName;
	}

	@Override
	public String toString() {
		return this.testerName + "(" + this.myTesterName + ")";
	}

	
}
