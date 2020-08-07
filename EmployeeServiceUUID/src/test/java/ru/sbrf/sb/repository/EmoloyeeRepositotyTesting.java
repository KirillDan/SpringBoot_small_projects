package ru.sbrf.sb.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javassist.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ru.sbrf.sb.entity.Department;
import ru.sbrf.sb.entity.Employee;
import ru.sbrf.sb.entity.Project;
import ru.sbrf.sb.repository.DepartmentRepository;
import ru.sbrf.sb.repository.EmployeeRepository;
import ru.sbrf.sb.repository.ProjectRepository;

@DataJpaTest
public class EmoloyeeRepositotyTesting {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private DepartmentRepository departmentRepository;

	private List<Employee> employeers;
	private List<Project> projects;
	private List<Department> departments;

	@BeforeEach
	public void setup() {
		assertNotNull(employeeRepository);
		assertNotNull(projectRepository);
		assertNotNull(departmentRepository);
		this.employeers = new ArrayList<Employee>();
		this.projects = new ArrayList<Project>();
		this.departments = new ArrayList<Department>();
		for (int i = 0; i < 10; i++) {
			employeers.add(new Employee().builder()
					.employeeId(UUID.randomUUID())
					.name(UUID.randomUUID().toString())
					.secondaryName(UUID.randomUUID().toString())
					.position("employee").build());
			projects.add(new Project().builder()
					.name(UUID.randomUUID().toString()).build());
			departments.add(new Department().builder()
					.name(UUID.randomUUID().toString()).build());
		}
	}
	
	@Test
	public void addEmployeers() {
		List<Employee> employeersResp = employeeRepository.saveAll(this.employeers);
		assertEquals(10, employeersResp.size());
		for(Employee emp : employeers) {
			assertNotNull(employeeRepository.findById(emp.getEmployeeId()));
		}
		employeeRepository.deleteAll();
	}
	
	@Test
	public void addProjects() {
		List<Project> projectsResp = projectRepository.saveAll(this.projects);
		assertEquals(10, projectsResp.size());
		for(Project pr : projects) {
			assertNotNull(projectRepository.findById(pr.getProjectId()));
		}
		projectRepository.deleteAll();
	}
	
	@Test
	public void AddDepartment() {
		List<Department> departmentsResp = departmentRepository.saveAll(this.departments);
		assertEquals(10, departmentsResp.size());
		for(Department dep : departments) {
			assertNotNull(departmentRepository.findById(dep.getDepartmentId()));
		}
		departmentRepository.deleteAll();
	}
	
	@Test
	public void addEmployeersIntoProject() {
		Employee employeeReq = this.employeers.get(0);
		Project projectReq = this.projects.get(0);
		projectReq.addEmployee(employeeReq);
		employeeReq.addProject(projectReq);
		Project projectResp = projectRepository.save(projectReq);
		Employee employeeResp = employeeRepository.save(employeeReq);
		
		assertEquals(1,employeeResp.getProjects().size());		
		assertEquals(1,projectResp.getEmployees().size());
		assertEquals(projectReq.getName(),employeeResp.getProjects().get(0).getName());
		assertEquals(employeeReq.getName(), projectResp.getEmployees().get(0).getName());
		employeeRepository.deleteAll();
		projectRepository.deleteAll();
	} 
	
	@Test
	public void addEmployeersIntoProjectAndDeleteEmployeersFromProject() throws NotFoundException {
		Employee employeeReq = this.employeers.get(0);
		Project projectReq = this.projects.get(0);
		projectReq.addEmployee(employeeReq);
		employeeReq.addProject(projectReq);
		Project projectResp = projectRepository.save(projectReq);
		Employee employeeResp = employeeRepository.save(employeeReq);
		
		assertEquals(1,employeeResp.getProjects().size());		
		assertEquals(1,projectResp.getEmployees().size());
		assertEquals(projectReq.getName(),employeeResp.getProjects().get(0).getName());
		assertEquals(employeeReq.getName(), projectResp.getEmployees().get(0).getName());
		
		projectResp.deleteEmployee(employeeResp);
		employeeResp.deleteProject(projectResp);
		Project projectRespDel = projectRepository.save(projectResp);
		Employee employeeRespDel = employeeRepository.save(employeeResp);
		
		assertTrue(employeeRespDel.getProjects().isEmpty());		
		assertTrue(projectRespDel.getEmployees().isEmpty());		
		employeeRepository.deleteAll();
		projectRepository.deleteAll();
	}
	
	@Test
	public void addEmployeersIntoDepartment() {
		Employee employeeReq = this.employeers.get(0);
		Department departmentReq = this.departments.get(0);
		departmentReq.addEmployee(employeeReq);
		employeeReq.addDepartment(departmentReq);
		Department departmentResp = departmentRepository.save(departmentReq);
		Employee employeeResp = employeeRepository.save(employeeReq);
		
		assertEquals(1,employeeResp.getEmployeeDepartments().size());		
		assertEquals(1,departmentResp.getEmployees().size());
		assertEquals(departmentReq.getName(),employeeResp.getEmployeeDepartments().get(0).getName());
		assertEquals(employeeReq.getName(), departmentResp.getEmployees().get(0).getName());
		employeeRepository.deleteAll();
		departmentRepository.deleteAll();
	} 
	
	@Test
	public void addEmployeersIntoDepartmentAndDeleteEmployeersFromDepartment() throws NotFoundException {
		Employee employeeReq = this.employeers.get(0);
		Department departmentReq = this.departments.get(0);
		departmentReq.addEmployee(employeeReq);
		employeeReq.addDepartment(departmentReq);
		Department departmentResp = departmentRepository.save(departmentReq);
		Employee employeeResp = employeeRepository.save(employeeReq);
		
		assertEquals(1,employeeResp.getEmployeeDepartments().size());		
		assertEquals(1,departmentResp.getEmployees().size());
		assertEquals(departmentReq.getName(),employeeResp.getEmployeeDepartments().get(0).getName());
		assertEquals(employeeReq.getName(), departmentResp.getEmployees().get(0).getName());
		
		departmentResp.deleteEmployee(employeeResp);
		employeeResp.deleteDepartment(departmentResp);
		Department departmentRespDel = departmentRepository.save(departmentResp);
		Employee employeeRespDel = employeeRepository.save(employeeResp);
		
		assertTrue(employeeRespDel.getEmployeeDepartments().isEmpty());		
		assertTrue(departmentRespDel.getEmployees().isEmpty());
		employeeRepository.deleteAll();
		departmentRepository.deleteAll();
	}
	
	@Test
	public void addEmployeersIntoProjectAndDepartment() {
		Employee employeeReq = this.employeers.get(0);
		Project projectReq = this.projects.get(0);
		Department departmentReq = this.departments.get(0);
		projectReq.addEmployee(employeeReq);
		employeeReq.addProject(projectReq);
		departmentReq.addEmployee(employeeReq);
		employeeReq.addDepartment(departmentReq);
		Department departmentResp = departmentRepository.save(departmentReq);
		Project projectResp = projectRepository.save(projectReq);
		Employee employeeResp = employeeRepository.save(employeeReq);
		assertEquals(1,employeeResp.getProjects().size());		
		assertEquals(1,projectResp.getEmployees().size());
		assertEquals(1,employeeResp.getEmployeeDepartments().size());		
		assertEquals(1,departmentResp.getEmployees().size());
		assertEquals(projectReq.getName(),employeeResp.getProjects().get(0).getName());
		assertEquals(employeeReq.getName(), projectResp.getEmployees().get(0).getName());
		assertEquals(departmentReq.getName(),employeeResp.getEmployeeDepartments().get(0).getName());
		assertEquals(employeeReq.getName(), departmentResp.getEmployees().get(0).getName());
		assertEquals(departmentReq.getName(), projectResp.getEmployees().get(0).getEmployeeDepartments().get(0).getName());
		assertEquals(projectReq.getName(), departmentResp.getEmployees().get(0).getProjects().get(0).getName());
		employeeRepository.deleteAll();
		departmentRepository.deleteAll();
		projectRepository.deleteAll();
	}
	
	@Test
	public void deleteEmployers() {
		Employee employeeResp = employeeRepository.save(this.employeers.get(0));
		employeeRepository.deleteById(employeeResp.getEmployeeId());
		assertTrue(employeeRepository.findById(employeeResp.getEmployeeId()).isEmpty());
	}
	
	@Test
	public void deleteProject() {
		Project projectResp = projectRepository.save(this.projects.get(0));
		projectRepository.deleteById(projectResp.getProjectId());
		assertTrue(projectRepository.findById(projectResp.getProjectId()).isEmpty());
	}
	
	@Test
	public void deleteDepartment() {
		Department departmentResp = departmentRepository.save(this.departments.get(0));
		departmentRepository.deleteById(departmentResp.getDepartmentId());
		assertTrue(departmentRepository.findById(departmentResp.getDepartmentId()).isEmpty());
	}
	
}
