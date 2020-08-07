package ru.sbrf.sb.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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

@Transactional
@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
	private EmployeeRepository employeeRepository;
	private ProjectRepository projectRepository;
	private DepartmentRepository departmentRepository;
	private EmployeeKeycloakRepository employeeKeycloakRepository;
	
	@Override
	public Employee createEmployee(Employee employee) {
		String id = employeeKeycloakRepository.createUser(
				employee.getName(),
				employee.getName(),
				employee.getSecondaryName(),
				employee.getName());
		employee.setEmployeeId(UUID.fromString(id));
		return employeeRepository.save(employee);
	}

	@Override
	public void deleteEmployee(UUID employeeId) {
		employeeRepository.deleteByEmployeeId(employeeId);
		employeeKeycloakRepository.deleteUser(employeeId);;
	}

	@Override
	public void editEmployee(Employee employee) throws NotFoundException {
		if(employeeRepository.existsByEmployeeId(employee.getEmployeeId())) {
			Employee employeeDB = employeeRepository.findByEmployeeId(employee.getEmployeeId()).get();
			employeeDB.setName(employee.getName());
			employeeDB.setSecondaryName(employee.getSecondaryName());
			employeeDB.setPosition(employee.getPosition());
			employeeRepository.save(employeeDB);
		} else {
			throw new NotFoundException("Department with Id = " + employee.getEmployeeId() + " does not exist\n");
		}
	}

	@Override
	public Employee getEmployee(UUID employeeId) {
		return employeeRepository.findByEmployeeId(employeeId).get();
	}

	@Override
	public List<Department> getEmployeeDepartments(UUID employeeId) {
		return employeeRepository.findByEmployeeId(employeeId).get().getEmployeeDepartments();
	}

	@Override
	public List<Project> getEmployeeProjects(UUID projectId) {
		return employeeRepository.findByEmployeeId(projectId).get().getProjects();
	}

	@Override
	public void addEmployeeToProject(UUID employeeId, UUID projectId) {
		Project project = projectRepository.findByProjectId(projectId).get();
		Employee employee = employeeRepository.findByEmployeeId(employeeId).get();
		project.addEmployee(employee);
		employee.addProject(project);
		projectRepository.save(project);
		employeeRepository.save(employee);
	}

	@Override
	public void deleteEmployeeFromProject(UUID employeeId, UUID projectId) throws NotFoundException {
		Project project = projectRepository.findByProjectId(projectId).get();
		Employee employee = employeeRepository.findByEmployeeId(employeeId).get();
		project.deleteEmployee(employee);
		employee.deleteProject(project);	
		projectRepository.save(project);
		employeeRepository.save(employee);	
	}

	@Override
	public void addEmployeeToDepartment(UUID employeeId, UUID departmentId) {
		Employee employee = employeeRepository.findByEmployeeId(employeeId).get();
		Department department = departmentRepository.findByDepartmentId(departmentId).get();
		employee.addDepartment(department);
		department.addEmployee(employee);
		departmentRepository.save(department);
		employeeRepository.save(employee);
	}

	@Override
	public void deleteEmployeeFromDepartment(UUID employeeId, UUID departmentId) throws NotFoundException {
		Employee employee = employeeRepository.findByEmployeeId(employeeId).get();
		Department department = departmentRepository.findByDepartmentId(departmentId).get();
		employee.deleteDepartment(department);
		department.deleteEmployee(employee);
		departmentRepository.save(department);
		employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getBossFromDepartment(UUID employeeId) {
		List<Department> departments = employeeRepository.findByEmployeeId(employeeId).get().getEmployeeDepartments();
		return departments.stream().flatMap(d -> d.getEmployees().stream()).filter(e -> e.getPosition().equals("boss")).collect(Collectors.toList());
	}

	@Override
	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}
}
