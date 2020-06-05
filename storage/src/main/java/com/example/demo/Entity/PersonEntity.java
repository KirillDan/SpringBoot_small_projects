package com.example.demo.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PersonEntity {
	@Id @GeneratedValue
	private long id;
	private String name;
	private String secondaryName;
	private String patronymic;
	private String address;
	public PersonEntity() {
	}
	public PersonEntity(String name, String secondaryName, String patronymic, String address) {
		super();
		this.name = name;
		this.secondaryName = secondaryName;
		this.patronymic = patronymic;
		this.address = address;
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSecondaryName() {
		return secondaryName;
	}
	public void setSecondaryName(String secondaryName) {
		this.secondaryName = secondaryName;
	}
	public String getPatronymic() {
		return patronymic;
	}
	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "PersonEntity [id=" + id + ", name=" + name + ", secondaryName=" + secondaryName + ", patronymic="
				+ patronymic + ", address=" + address + "]";
	}
	
	
}
