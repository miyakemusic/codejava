package com.miyake.demo.entities;

public enum UserRole {
	Administrator(0),
	User(1);

	private int role;

	UserRole(int role) {
		this.role = role;
	}

	public int getRole() {
		return role;
	}
	
}
