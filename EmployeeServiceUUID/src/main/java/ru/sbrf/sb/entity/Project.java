package ru.sbrf.sb.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Builder
public class Project {
	@Id @GeneratedValue
	private UUID projectId;
	
	private String name;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(
            name = "project_employee",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
	private List<Employee> employees;
	
	public void addEmployee(Employee employee) {
		if(this.employees == null) {
			this.employees = new ArrayList<Employee>();
		}
		this.employees.add(employee);
	}
	
	public void deleteEmployee(Employee employee) throws NotFoundException {
		if(this.employees == null) {
			throw new NotFoundException("The project have not employees");
		} else {
			if(employees.remove(employee)) {
				return;
			} else {
				throw new NotFoundException("The project does not have this user");
			}
		}
	}
}