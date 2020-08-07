package ru.sbrf.sb.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import ru.sbrf.sb.entity.Employee;
import ru.sbrf.sb.keycloak.EmployeeKeycloakRepository;
import ru.sbrf.sb.keycloak.impl.EmployeeKeycloakRepositoryImpl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KeycloakRepositoryTesting {
	@Bean
	public Keycloak keycloak() {
		return Keycloak.getInstance(
			    "http://localhost:8080/auth",
			    "master",
			    "admin",
			    "admin",
			    "admin-cli",
			    "117b80c5-10b0-453b-bafc-d84e5b1d5b5b");
	}
	
	@Bean
	public EmployeeKeycloakRepository mployeeKeycloakRepository(Keycloak keycloak) {
		return new EmployeeKeycloakRepositoryImpl(keycloak);
	}
	
	@Autowired
	private EmployeeKeycloakRepository keycloakRepository;
	
	private List<Employee> employeers;
	
	@BeforeEach
	public void setup() {
		assertNotNull(keycloakRepository);
		this.employeers = new ArrayList<Employee>();
		for (int i = 0; i < 10; i++) {
			employeers.add(new Employee().builder()
					//.employeeId(UUID.randomUUID())
					.name(UUID.randomUUID().toString())
					.secondaryName(UUID.randomUUID().toString())
					.position("employee").build());
		}
	}

	@Test
	public void addAndDeleteEmployeers(){
		List<String> ids = new ArrayList<String>();
		for(Employee emp : employeers) {
			ids.add(
					keycloakRepository.createUser(
					emp.getPosition() + "_" + UUID.randomUUID().toString(), 
					emp.getName(), 
					emp.getSecondaryName(), 
					emp.getPosition())
					);
		}
		assertEquals(employeers.size(), ids.size());
		for(String id : ids) {
			keycloakRepository.deleteUser(id);
		}
	}
	
	@Test
	public void addAndGetAndDeleteUser() {
		Employee emp = employeers.get(0);
		String id = keycloakRepository.createUser(
				emp.getPosition() + "_" + UUID.randomUUID().toString(), 
				emp.getName(), 
				emp.getSecondaryName(), 
				emp.getPosition());
		Employee empRes = keycloakRepository.getUser(id);
		assertEquals(emp.getName(),empRes.getName());
		assertEquals(emp.getSecondaryName(),empRes.getSecondaryName());
		keycloakRepository.deleteUser(id);
	}
	
	@Test
	public void addAndGetAndUpdateAndGetAndDeleteUser() {
		Employee emp = employeers.get(0);
		String id = keycloakRepository.createUser(
				emp.getPosition() + "_" + UUID.randomUUID().toString(), 
				emp.getName(), 
				emp.getSecondaryName(), 
				emp.getPosition());
		Employee empRes = keycloakRepository.getUser(id);
		assertEquals(emp.getName(),empRes.getName());
		assertEquals(emp.getSecondaryName(),empRes.getSecondaryName());
		emp.setEmployeeId(UUID.fromString(id));
		emp.setName(UUID.randomUUID().toString());
		emp.setSecondaryName(UUID.randomUUID().toString());
		keycloakRepository.changeUser(emp);
		Employee empResNew = keycloakRepository.getUser(id);
		assertEquals(emp.getName(),empResNew.getName());
		assertEquals(emp.getSecondaryName(),empResNew.getSecondaryName());
		keycloakRepository.deleteUser(id);
	}

}
