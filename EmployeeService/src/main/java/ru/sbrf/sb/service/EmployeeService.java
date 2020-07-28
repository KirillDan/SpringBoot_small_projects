package ru.sbrf.sb.service;

import ru.sbrf.sb.entity.Employee;

import java.util.List;

import javassist.NotFoundException;
import ru.sbrf.sb.entity.Department;
import ru.sbrf.sb.entity.Project;

public interface EmployeeService {
//CRUD операции
	public Employee createEmployee(Employee employee);
	public void deleteEmployee(Long employeeId);
	public void editEmployee(Employee employee) throws NotFoundException;
	public Employee getEmployee(Long employeeId);
	public List<Employee> getEmployees();
//Дополнительные данные
	public List<Department> getEmployeeDepartments(Long employeeId);
	public List<Project> getEmployeeProjects(Long projectId);
//Дополнительные операции
	//Project
	public void addEmployeeToProject(Long employeeId, Long projectId);
	public void deleteEmployeeFromProject(Long employeeId, Long projectId) throws NotFoundException;
	//Department
	public void addEmployeeToDepartment(Long employeeId, Long departmentId);
	public void deleteEmployeeFromDepartment(Long employeeId, Long departmentId) throws NotFoundException;
	public List<Employee> getBossFromDepartment(Long employeeId);
}
