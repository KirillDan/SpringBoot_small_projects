package ru.sbrf.sb.service;

import ru.sbrf.sb.entity.Employee;

import java.util.List;
import java.util.UUID;

import javassist.NotFoundException;
import ru.sbrf.sb.entity.Department;
import ru.sbrf.sb.entity.Project;

public interface EmployeeService {
//CRUD операции
	public Employee createEmployee(Employee employee);
	public void deleteEmployee(UUID employeeId);
	public void editEmployee(Employee employee) throws NotFoundException;
	public Employee getEmployee(UUID employeeId);
	public List<Employee> getEmployees();
//Дополнительные данные
	public List<Department> getEmployeeDepartments(UUID employeeId);
	public List<Project> getEmployeeProjects(UUID projectId);
//Дополнительные операции
	//Project
	public void addEmployeeToProject(UUID employeeId, UUID projectId);
	public void deleteEmployeeFromProject(UUID employeeId, UUID projectId) throws NotFoundException;
	//Department
	public void addEmployeeToDepartment(UUID employeeId, UUID departmentId);
	public void deleteEmployeeFromDepartment(UUID employeeId, UUID departmentId) throws NotFoundException;
	public List<Employee> getBossFromDepartment(UUID employeeId);
}
