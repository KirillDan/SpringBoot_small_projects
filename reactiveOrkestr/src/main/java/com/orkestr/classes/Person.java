package com.orkestr.classes;



public class Person{
	private Long id;
	private String name;
	private String secondaryName;
	private String patronymic;
	private String address;
	public Person() {
	}
	public Person(String name, String secondaryName, String patronymic, String address) {
		this.name = name;
		this.secondaryName = secondaryName;
		this.patronymic = patronymic;
		this.address = address;
	}
	public Person(Long id, String name, String secondaryName, String patronymic, String address) {
		this(name, secondaryName, patronymic, address);
		this.id = id;

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
