package ru.sbrf.sb.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ru.sbrf.sb.entity.Department;
import ru.sbrf.sb.entity.Employee;
import ru.sbrf.sb.entity.Project;

@DataJpaTest
public class EmployeeServiceTesting {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private ProjectService projectService;
	
	private List<Employee> employeers;
	private List<Project> projects;
	private List<Department> departments;
	
	@BeforeEach
	public void setup() {
		assertNotNull(employeeService);
		assertNotNull(departmentService);
		assertNotNull(projectService);
	}
}
