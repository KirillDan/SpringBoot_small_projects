package com.example.demo.entity;

public class IdAddress {
	private long id;
	private String address;
	
	public IdAddress(long id, String address) {
		super();
		this.id = id;
		this.address = address;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
