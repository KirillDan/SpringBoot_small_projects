package ru.sbrf.sb.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.sun.istack.NotNull;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
public class Employee {
	@Id @GeneratedValue
	private Long employeeId;
	@NotNull
	private String name;
	@NotNull
	private String secondaryName;
	@NotNull
	private String position;
	
	@ManyToMany
	private List<Department> employeeDepartments;
	
	public void addDepartment(Department employeeDepartment) {
		if(this.employeeDepartments == null) {
			this.employeeDepartments = new ArrayList<Department>();
		}
		this.employeeDepartments.add(employeeDepartment);
	}
	
	public void deleteDepartment(Department department) throws NotFoundException {
		if(this.employeeDepartments == null) {
			throw new NotFoundException("The Employee have not Department");
		} else {
			if(employeeDepartments.remove(department)) {
				return;
			} else {
				throw new NotFoundException("The Employee does not have this Department");
			}
		}
	}
	
	@ManyToMany
	private List<Project> projects;
	
	public void addProject(Project project) {
		if(this.projects == null) {
			this.projects = new ArrayList<Project>();
		}
		this.projects.add(project);
	}
	
	public void deleteProject(Project project) throws NotFoundException {
		if(this.projects == null) {
			throw new NotFoundException("The Employee have not project");
		} else {
			if(projects.remove(project)) {
				return;
			} else {
				throw new NotFoundException("The Employee does not have this project");
			}
		}
	}
}
