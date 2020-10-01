package org.cyk.utility.__kernel__.security.keycloak;

import java.util.List;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.junit.jupiter.api.Test;

public class KeycloakPlaygroundUnitTestIntegration extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		KeycloakClientGetter.INSTANCE.set(null);
	}
	
	private void init() {
		VariableHelper.write(VariableName.KEYCLOAK_ENABLED, Boolean.TRUE);
		VariableHelper.write(VariableName.KEYCLOAK_SERVER_URL, "http://localhost:8230/auth");
		VariableHelper.write(VariableName.KEYCLOAK_REALM_NAME, "Tutorial");
		VariableHelper.write(VariableName.KEYCLOAK_CLIENT_IDENTIFIER, "admin-cli");
		VariableHelper.write(VariableName.KEYCLOAK_CLIENT_SECRET, "ef4175c9-c803-4925-b099-1beb923a89f1");
		VariableHelper.write(VariableName.KEYCLOAK_CREDENTIAL_USERNAME, "admin");
		VariableHelper.write(VariableName.KEYCLOAK_CREDENTIAL_PASSWORD, "admin");
	}
	
	@Test
	public void setup(){
		init();
		
		ClientManager.getInstance().deleteAllAuthorizationPolicies("application01_02");
		ClientManager.getInstance().deleteAllAuthorizationResources("application01_02");		
		
		ClientManager.getInstance().createAuthorizationPoliciesFromRolesNames(List.of("application01_02"), "user","r01","r02","r03","r04");
		ClientManager.getInstance().createAuthorizationResources(List.of("application01_02"), List.of(
				Resource.build("Espace prive","/*")
				,Resource.build("Espace de r01","/private/r01/*")
				,Resource.build("Espace de r02","/private/r02/*")
				,Resource.build("Fichier de r03","/private/r03.jsf*")
				));
		
		ClientManager.getInstance().createAuthorizationPermissionFromRolesNamesAndResourcesNames(List.of("application01_02")
				, List.of("user")
				, List.of("Espace prive")
			);
		ClientManager.getInstance().createAuthorizationPermissionFromRolesNamesAndResourcesNames(List.of("application01_02")
				, List.of("r01")
				, List.of("Espace de r01")
			);
		ClientManager.getInstance().createAuthorizationPermissionFromRolesNamesAndResourcesNames(List.of("application01_02")
				, List.of("r02")
				, List.of("Espace de r02")
			);
		ClientManager.getInstance().createAuthorizationPermissionFromRolesNamesAndResourcesNames(List.of("application01_02")
				, List.of("r03")
				, List.of("Fichier de r03")
			);
	}
}