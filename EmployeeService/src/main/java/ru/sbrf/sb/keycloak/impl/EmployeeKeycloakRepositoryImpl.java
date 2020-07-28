package ru.sbrf.sb.keycloak.impl;

import java.util.Collections;

import javax.ws.rs.core.Response;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import ru.sbrf.sb.keycloak.EmployeeKeycloakRepository;

@Component
@NoArgsConstructor
public class EmployeeKeycloakRepositoryImpl implements EmployeeKeycloakRepository {
	
	@Override
	public void createUser(String userName, String firstName, String lastName, String password) {
	    final String realm = "Demo-Realm";
	    Keycloak keycloak = Keycloak.getInstance(
			    "http://localhost:8080/auth",
			    "master",
			    "admin",
			    "admin",
			    "admin-cli",
			    "117b80c5-10b0-453b-bafc-d84e5b1d5b5b");
		RealmResource realmResource = keycloak.realm(realm);
		UsersResource usersResource = realmResource.users();
		
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
		
		
		
		
//		Keycloak keycloak = Keycloak.getInstance(
//			    "http://localhost:8080/auth",
//			    "master",
//			    "admin",
//			    "admin",
//			    "admin-cli",
//			    "117b80c5-10b0-453b-bafc-d84e5b1d5b5b");
//		RealmResource realmResource = keycloak.realm("Demo-Realm");
//	    UsersResource usersResource = realmResource.users();
//		
//		UserRepresentation userRepresentation = new UserRepresentation();
//		userRepresentation.setUsername("randomUser");
//		userRepresentation.setFirstName("firstName");
//		userRepresentation.setLastName("lastName");
//		
//		CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
//	    credentialRepresentation.setTemporary(false);
//	    credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
//	    credentialRepresentation.setValue("mypassword");
//	    userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
//		
//	    Response response = usersResource.create(userRepresentation);
	}

}
