package com.reactiveStorage.entity;

import java.util.Random;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public class User {
	@Id 
	private Long id;
	private String name;
	private String address;
	public User() {
	}
	public User(String name, String address) {
		this.name = name;
		this.address = address;
		this.id = Math.abs((long) (new Random()).nextInt());
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "PersonEntity [id=" + id + ", name=" + name + ", address=" + address + "]";
	}
}
