package org.cyk.utility.security.keycloak.server.sigobe;

import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.security.keycloak.server.ClientManager;
import org.cyk.utility.security.keycloak.server.KeycloakClientGetter;
import org.cyk.utility.security.keycloak.server.Resource;
import org.cyk.utility.security.keycloak.server.Role;
import org.cyk.utility.security.keycloak.server.User;
import org.cyk.utility.security.keycloak.server.UserManager;
import org.cyk.utility.__kernel__.test.weld.AbstractPersistenceUnitTest;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.junit.jupiter.api.Test;

public abstract class AbstractSIGOBEKeycloakIntegrationUnitTest extends AbstractPersistenceUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		KeycloakClientGetter.INSTANCE.set(null);
	}
	
	protected abstract String getRealm();
	
	private void init() {
		VariableHelper.write(VariableName.KEYCLOAK_ENABLED, Boolean.TRUE);
		VariableHelper.write(VariableName.KEYCLOAK_SERVER_URL, "http://10.3.4.60:8080/auth/");
		VariableHelper.write(VariableName.KEYCLOAK_REALM_NAME, getRealm());
		VariableHelper.write(VariableName.KEYCLOAK_CLIENT_IDENTIFIER, "admin-cli");
		VariableHelper.write(VariableName.KEYCLOAK_CLIENT_SECRET, "ac0749e3-14f8-4a14-a6e1-4edfb9375e69");
		VariableHelper.write(VariableName.KEYCLOAK_CREDENTIAL_USERNAME, "mic-acteur-api");
		VariableHelper.write(VariableName.KEYCLOAK_CREDENTIAL_PASSWORD, "mic-@cteur-@pi@2O2o");
	}
	
	@Test
	public void run(){
		init();
		setDefaultRoleToAllUsers();
		deleteClientResourcesAndPolicies();
		createClientResourcesAndPolicies();
		createClientPermissions();
	}
	
	private void setDefaultRoleToAllUsers(){
		Collection<User> users = UserManager.getInstance().readAll();
		if(CollectionHelper.isEmpty(users))
			return;
		users.forEach(user -> {
			user.addRoles(new Role("UTILISATEUR"));
		});
		System.out.println("Setting default role <<UTILISATEUR>> to "+users.size()+" users");
		UserManager.getInstance().updateRoles(users);			
	}
	
	private void deleteClientResourcesAndPolicies(){
		for(String clientName : CLIENTS_NAMES) {
			ClientManager.getInstance().deleteAllAuthorizationPolicies(clientName);
			ClientManager.getInstance().deleteAllAuthorizationResources(clientName);		
		}
	}
	
	private void createClientResourcesAndPolicies(){
		for(String clientName : CLIENTS_NAMES) {
			ClientManager.getInstance().createAuthorizationPoliciesFromRolesNames(List.of(clientName), "UTILISATEUR","ADMINISTRATEUR");
			ClientManager.getInstance().createAuthorizationResources(List.of(clientName), List.of(
					Resource.build("Espace prive","/*")
					,Resource.build("Espace ADMINISTRATEUR","/parametrage/*")
					//,Resource.build("Espace de r02","/private/r02/*")
					//,Resource.build("Fichier de r03","/private/r03.jsf*")
					));
		}
	}
	
	private void createClientPermissions(){
		for(String clientName : CLIENTS_NAMES) {
			ClientManager.getInstance().createAuthorizationPermissionFromRolesNamesAndResourcesNames(List.of(clientName)
					, List.of("UTILISATEUR")
					, List.of("Espace prive")
				);
			ClientManager.getInstance().createAuthorizationPermissionFromRolesNamesAndResourcesNames(List.of(clientName)
					, List.of("ADMINISTRATEUR")
					, List.of("Espace ADMINISTRATEUR")
				);
		}
	}
	
	private static final String[] CLIENTS_NAMES = {"mic-enveloppe-budgetaire"};
}