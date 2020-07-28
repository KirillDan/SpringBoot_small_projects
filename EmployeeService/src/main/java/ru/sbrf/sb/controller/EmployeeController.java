package ru.sbrf.sb.controller;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.Response;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	Keycloak keycloak;
	
	@GetMapping("/test")
	public void test() {
		
		RealmResource realmResource = keycloak.realm("Demo-Realm");
	    UsersResource usersResource = realmResource.users();
		
		UserRepresentation userRepresentation = new UserRepresentation();
		userRepresentation.setUsername("randomUser");
		userRepresentation.setFirstName("firstName");
		userRepresentation.setLastName("lastName");
		
		CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
	    credentialRepresentation.setTemporary(false);
	    credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
	    credentialRepresentation.setValue("mypassword");
	    userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
		
	    Response response = usersResource.create(userRepresentation);
	}
	
	@GetMapping("/{id}")
	public Employee get(@PathVariable Long id) {
		return employeeService.getEmployee(id);
	}
	
	@GetMapping()
	public List<Employee> get() {
		return employeeService.getEmployees();
	}
	
	@PostMapping()
	public Employee create(@RequestBody Employee employee) {
		return employeeService.createEmployee(employee);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		employeeService.deleteEmployee(id);;
	}
	
	@PutMapping()
	public void edit(@RequestBody Employee employee) throws NotFoundException {
		employeeService.editEmployee(employee);
	}
	
	@GetMapping("/{id}/departments")
	public List<Department> getDepartments(@PathVariable Long id) {
		return employeeService.getEmployeeDepartments(id);
	}
	
	@GetMapping("/{id}/projects")
	public List<Project> getProjects(@PathVariable Long id) {
		return employeeService.getEmployeeProjects(id);
	}
	
	@PutMapping("/{employee_id}/project/{project_id}")
	public void addEmployeeToProject(@PathVariable Long employee_id, @PathVariable Long project_id) {
		employeeService.addEmployeeToProject(employee_id, project_id);
	}
	
	@DeleteMapping("/{employee_id}/project/{project_id}")
	public void deleteEmployeeFromProject(@PathVariable Long employee_id, @PathVariable Long project_id) throws NotFoundException {
		employeeService.deleteEmployeeFromProject(employee_id, project_id);
	}
	
	@PutMapping("/{employee_id}/department/{department_id}")
	public void addEmployeeToDepartment(@PathVariable Long employee_id, @PathVariable Long department_id) {
		employeeService.addEmployeeToDepartment(employee_id, department_id);
	}
	
	@DeleteMapping("/{employee_id}/department/{department_id}")
	public void deleteEmployeeFromDepartment(@PathVariable Long employee_id, @PathVariable Long department_id) throws NotFoundException {
		employeeService.deleteEmployeeFromDepartment(employee_id, department_id);
	}
	
	@GetMapping("/{id}/boss")
	public List<Employee> getBoss(@PathVariable Long id) {
		return employeeService.getBossFromDepartment(id);
	}
}
