package ru.sbrf.sb.keycloak;

public interface EmployeeKeycloakRepository {
	public void createUser(String userName, String firstName, String lastName, String password);
}
