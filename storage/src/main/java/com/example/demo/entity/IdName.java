package com.example.demo.entity;

public class IdName {
		private Long id;
		private String name;

		public IdName(Long id, String name) {
			this.name = name;
			this.id = id;
		}
		public long getId() {
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
		@Override
		public String toString() {
			return "IdName [id=" + id + ", name=" + name + "]";
		}		
}
