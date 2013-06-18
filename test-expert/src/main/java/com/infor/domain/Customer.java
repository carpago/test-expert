package com.infor.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Customer implements Serializable {
	
	private int id;
	private String name;
	
	public Customer(int id, String name) {
		this.id = id;
		this.name = name;
	}

	protected int getId() {
		return id;
	}

	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

}
