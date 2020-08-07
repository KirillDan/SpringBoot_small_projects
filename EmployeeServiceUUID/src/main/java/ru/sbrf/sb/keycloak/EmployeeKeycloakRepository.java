package ru.sbrf.sb.keycloak;

import java.util.UUID;

import ru.sbrf.sb.entity.Employee;

public interface EmployeeKeycloakRepository {
	public String createUser(String userName, String firstName, String lastName, String password);
	public void deleteUser(UUID id);
	public void changeUser(Employee employee);
	public void deleteUser(String id);
	public Employee getUser(String userId);
}
