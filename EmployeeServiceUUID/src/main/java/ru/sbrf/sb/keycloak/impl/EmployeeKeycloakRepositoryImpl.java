package ru.sbrf.sb.keycloak.impl;

import java.util.Collections;
import java.util.UUID;

import javax.ws.rs.core.Response;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.sbrf.sb.entity.Employee;
import ru.sbrf.sb.keycloak.EmployeeKeycloakRepository;

@Component
public class EmployeeKeycloakRepositoryImpl implements EmployeeKeycloakRepository {
	
	private RealmResource realmResource;
	private UsersResource usersResource;
	
	@Autowired
	public EmployeeKeycloakRepositoryImpl(Keycloak keycloak) {
		this.realmResource = keycloak.realm(realm);
		this.usersResource = realmResource.users();
	}

	final static String realm = "Demo-Realm";
	
	@Override
	public String createUser(String userName, String firstName, String lastName, String password) {
		UserRepresentation userRepresentation = new UserRepresentation();
		userRepresentation.setUsername(userName);
		userRepresentation.setFirstName(firstName);
		userRepresentation.setLastName(lastName);
		
		CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
	    credentialRepresentation.setTemporary(false);
	    credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
	    credentialRepresentation.setValue(password);
	    userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
		
	    Response response = usersResource.create(userRepresentation);
	    return response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
	}

	@Override
	public void deleteUser(UUID id) {
		usersResource.delete(id.toString());
	}
	
	@Override
	public void deleteUser(String id) {
		usersResource.delete(id);
	}

	@Override
	public void changeUser(Employee employee) {
		UserResource userResource = usersResource.get(employee.getEmployeeId().toString());
		
		UserRepresentation userRepresentation2 = userResource.toRepresentation();
		userRepresentation2.setFirstName(employee.getName());
		userRepresentation2.setLastName(employee.getSecondaryName());
		userResource.update(userRepresentation2);	
	}
	
	public Employee getUser(String userId) {
		String name = usersResource.get(userId).toRepresentation().getFirstName();
		String secondaryName = usersResource.get(userId).toRepresentation().getLastName();
		return new Employee(UUID.fromString(userId), name, secondaryName, null, null, null);
	}
	

}
