package com.miyake.demo.jsonobject;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MyTesterJson {

	private Long id;
	private String category;
	private String vendor;
	private String myTesterName;
	private String testerName;
	private Long parentid;
	private String parentText;
	private String status;
	private String description;
	private boolean standalone;
	private List<Long> parentCandidates = new ArrayList<>();
	
	public MyTesterJson() {}
	public MyTesterJson(Long id, String myTesterName, String testerName) {
		this.id = id;
		this.myTesterName = myTesterName;
		this.testerName = testerName;		
	}
	public MyTesterJson(Long id, String myTesterName, String testerName, List<Long> parentCandidates) {
		this.id = id;
		this.myTesterName = myTesterName;
		this.testerName = testerName;
		this.parentCandidates = parentCandidates;
	}

	@Override
	public String toString() {
		return this.testerName + "(" + this.myTesterName + ")";
	}

	
}
