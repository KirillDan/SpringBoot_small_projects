package com.example.demo.entity;

import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id @GeneratedValue
	private Long id;
	private String name;
	private String address;
	public User() {
	}
	public User(String name, String address) {
		this.name = name;
		this.address = address;
		this.id = (new Random()).nextLong();
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
