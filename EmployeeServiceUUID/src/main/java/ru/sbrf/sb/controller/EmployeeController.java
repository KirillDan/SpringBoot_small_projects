package ru.sbrf.sb.controller;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import ru.sbrf.sb.entity.Department;
import ru.sbrf.sb.entity.Employee;
import ru.sbrf.sb.entity.Project;
import ru.sbrf.sb.service.EmployeeService;

@RestController
@RequestMapping("sb/employee")
@AllArgsConstructor
public class EmployeeController {
	private EmployeeService employeeService;

	
	@GetMapping("/{id}")
	public Employee get(@PathVariable UUID id) {
		return employeeService.getEmployee(id);
	}
	
	@GetMapping
	public List<Employee> get() {
		return employeeService.getEmployees();
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Employee create(@RequestBody Employee employee) {
		return employeeService.createEmployee(employee);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		employeeService.deleteEmployee(id);
	}
	
	@PutMapping()
	public void edit(@RequestBody Employee employee) throws NotFoundException {
		employeeService.editEmployee(employee);
	}
	
	@GetMapping("/{id}/departments")
	public List<Department> getDepartments(@PathVariable UUID id) {
		return employeeService.getEmployeeDepartments(id);
	}
	
	@GetMapping("/{id}/projects")
	public List<Project> getProjects(@PathVariable UUID id) {
		return employeeService.getEmployeeProjects(id);
	}
	
	@PutMapping("/{employee_id}/project/{project_id}")
	public void addEmployeeToProject(@PathVariable UUID employee_id, @PathVariable UUID project_id) {
		employeeService.addEmployeeToProject(employee_id, project_id);
	}
	
	@DeleteMapping("/{employee_id}/project/{project_id}")
	public void deleteEmployeeFromProject(@PathVariable UUID employee_id, @PathVariable UUID project_id) throws NotFoundException {
		employeeService.deleteEmployeeFromProject(employee_id, project_id);
	}
	
	@PutMapping("/{employee_id}/department/{department_id}")
	public void addEmployeeToDepartment(@PathVariable UUID employee_id, @PathVariable UUID department_id) {
		employeeService.addEmployeeToDepartment(employee_id, department_id);
	}
	
	@DeleteMapping("/{employee_id}/department/{department_id}")
	public void deleteEmployeeFromDepartment(@PathVariable UUID employee_id, @PathVariable UUID department_id) throws NotFoundException {
		employeeService.deleteEmployeeFromDepartment(employee_id, department_id);
	}
	
	@GetMapping("/{id}/boss")
	public List<Employee> getBoss(@PathVariable UUID id) {
		return employeeService.getBossFromDepartment(id);
	}
}
