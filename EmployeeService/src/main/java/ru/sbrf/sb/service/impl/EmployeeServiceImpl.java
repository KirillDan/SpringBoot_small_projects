package ru.sbrf.sb.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import ru.sbrf.sb.entity.Department;
import ru.sbrf.sb.entity.Employee;
import ru.sbrf.sb.entity.Project;
import ru.sbrf.sb.keycloak.EmployeeKeycloakRepository;
import ru.sbrf.sb.repository.DepartmentRepository;
import ru.sbrf.sb.repository.EmployeeRepository;
import ru.sbrf.sb.repository.ProjectRepository;
import ru.sbrf.sb.service.EmployeeService;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
	private EmployeeRepository employeeRepository;
	private ProjectRepository projectRepository;
	private DepartmentRepository departmentRepository;
	private EmployeeKeycloakRepository employeeKeycloakRepository;
	
	@Override
	public Employee createEmployee(Employee employee) {
		employeeKeycloakRepository.createUser(
				employee.getName(),
				employee.getName(),
				employee.getSecondaryName(),
				employee.getName());
		return employeeRepository.save(employee);
	}

	@Override
	public void deleteEmployee(Long employeeId) {
		employeeRepository.deleteById(employeeId);
	}

	@Override
	public void editEmployee(Employee employee) throws NotFoundException {
		if(employeeRepository.existsById(employee.getEmployeeId())) {
			employeeRepository.save(employee);
		} else {
			throw new NotFoundException("Department with Id = " + employee.getEmployeeId() + " does not exist\n");
		}
	}

	@Override
	public Employee getEmployee(Long employeeId) {
		return employeeRepository.findById(employeeId).get();
	}

	@Override
	public List<Department> getEmployeeDepartments(Long employeeId) {
		return employeeRepository.findById(employeeId).get().getEmployeeDepartments();
	}

	@Override
	public List<Project> getEmployeeProjects(Long projectId) {
		return employeeRepository.findById(projectId).get().getProjects();
	}

	@Override
	public void addEmployeeToProject(Long employeeId, Long projectId) {
		Project project = projectRepository.findById(projectId).get();
		Employee employee = employeeRepository.findById(employeeId).get();
		project.addEmployee(employee);
		employee.addProject(project);
		projectRepository.save(project);
		employeeRepository.save(employee);
	}

	@Override
	public void deleteEmployeeFromProject(Long employeeId, Long projectId) throws NotFoundException {
		Project project = projectRepository.findById(projectId).get();
		Employee employee = employeeRepository.findById(employeeId).get();
		project.deleteEmployee(employee);
		employee.deleteProject(project);	
		projectRepository.save(project);
		employeeRepository.save(employee);	
	}

	@Override
	public void addEmployeeToDepartment(Long employeeId, Long departmentId) {
		Employee employee = employeeRepository.findById(employeeId).get();
		Department department = departmentRepository.findById(departmentId).get();
		employee.addDepartment(department);
		department.addEmployee(employee);
		departmentRepository.save(department);
		employeeRepository.save(employee);
	}

	@Override
	public void deleteEmployeeFromDepartment(Long employeeId, Long departmentId) throws NotFoundException {
		Employee employee = employeeRepository.findById(employeeId).get();
		Department department = departmentRepository.findById(departmentId).get();
		employee.deleteDepartment(department);
		department.deleteEmployee(employee);
		departmentRepository.save(department);
		employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getBossFromDepartment(Long employeeId) {
		List<Department> departments = employeeRepository.findById(employeeId).get().getEmployeeDepartments();
		return departments.stream().flatMap(d -> d.getEmployees().stream()).filter(e -> e.getPosition().equals("boss")).collect(Collectors.toList());
	}

	@Override
	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}
}
