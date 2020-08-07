package ru.sbrf.sb.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.test.context.support.WithMockUser;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.sbrf.sb.entity.Department;
import ru.sbrf.sb.entity.Employee;
import ru.sbrf.sb.entity.Project;
import ru.sbrf.sb.service.EmployeeService;

@AutoConfigureMockMvc
@SpringBootTest
public class ControllerTesting {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private EmployeeService employeeService;
	
	private List<Employee> employeers;
	private List<Project> projects;
	private List<Department> departments;
	
	@BeforeEach
	public void setup() {
		assertNotNull(mockMvc);
		assertNotNull(employeeService);
		this.employeers = new ArrayList<Employee>();
		this.projects = new ArrayList<Project>();
		this.departments = new ArrayList<Department>();
		for (int i = 0; i < 10; i++) {
			employeers.add(new Employee().builder()
					.employeeId(UUID.randomUUID())
					.name(UUID.randomUUID().toString())
					.secondaryName(UUID.randomUUID().toString())
					.position("employee").build());
		}
		projects.add(new Project().builder()
				.name(UUID.randomUUID().toString()).build());
		departments.add(new Department().builder()
				.name(UUID.randomUUID().toString()).build());
		Mockito.when(employeeService.getEmployees()).thenReturn(employeers);
		Mockito.when(employeeService.getEmployee(any())).thenReturn(
				new Employee().builder()
				.employeeId(UUID.randomUUID())
				.name(UUID.randomUUID().toString())
				.secondaryName(UUID.randomUUID().toString())
				.position("employee").build()
		);
		Mockito.when(employeeService.createEmployee(employeers.get(0))).thenReturn(employeers.get(0));
		Mockito.when(employeeService.getEmployeeDepartments(any())).thenReturn(departments);
		Mockito.when(employeeService.getEmployeeProjects(any())).thenReturn(projects);
		List<Employee> boss = new ArrayList<Employee>();
		boss.add(new Employee().builder()
				.employeeId(UUID.randomUUID())
				.name(UUID.randomUUID().toString())
				.secondaryName(UUID.randomUUID().toString())
				.position("boss").build()
				);
		Mockito.when(employeeService.getBossFromDepartment(any())).thenReturn(boss);
	}
	
	//@WithMockUser("USER")
	@Test
	public void getEmployeers() throws Exception {
		ResultActions resultActions = mockMvc.perform(
				get("/sb/employee")
				.accept(MediaType.APPLICATION_JSON))
			      .andDo(print());
		resultActions.andExpect(status().is2xxSuccessful());
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(10)));	
	}
	
	@Test
	public void getEmployee() throws Exception {
		ResultActions resultActions = mockMvc.perform(
				get("/sb/employee/" + UUID.randomUUID())
				.accept(MediaType.APPLICATION_JSON))
			      .andDo(print());
		resultActions.andExpect(status().is2xxSuccessful());
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());	
	}
	
	@Test
	public void postEmployee() throws Exception {
		ResultActions resultActions = mockMvc.perform(
				post("/sb/employee")
				.content(new ObjectMapper().writeValueAsString(
						employeers.get(0)
				))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			      .andDo(print());
		resultActions.andExpect(status().isCreated());
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());	
	}
	
	@Test
	public void getEmployeeDepartments() throws Exception {
		ResultActions resultActions = mockMvc.perform(
				get("/sb/employee/" + UUID.randomUUID() + "/departments")
				.accept(MediaType.APPLICATION_JSON))
			      .andDo(print());
		resultActions.andExpect(status().is2xxSuccessful());
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());	
	}
	
	@Test
	public void getEmployeeProjects() throws Exception {
		ResultActions resultActions = mockMvc.perform(
				get("/sb/employee/" + UUID.randomUUID() + "/projects")
				.accept(MediaType.APPLICATION_JSON))
			      .andDo(print());
		resultActions.andExpect(status().is2xxSuccessful());
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());	
	}
	
	@Test
	public void getEmployeeBoss() throws Exception {
		ResultActions resultActions = mockMvc.perform(
				get("/sb/employee/" + UUID.randomUUID() + "/boss")
				.accept(MediaType.APPLICATION_JSON))
			      .andDo(print());
		resultActions.andExpect(status().is2xxSuccessful());
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());	
	}
}
