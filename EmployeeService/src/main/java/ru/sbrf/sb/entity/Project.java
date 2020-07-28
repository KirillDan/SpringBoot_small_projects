package ru.sbrf.sb.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
public class Project {
	@Id @GeneratedValue
	private Long projectId;
	
	@ManyToMany
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