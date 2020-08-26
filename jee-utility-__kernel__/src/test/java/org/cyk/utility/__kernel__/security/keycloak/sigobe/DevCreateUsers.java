package org.cyk.utility.__kernel__.security.keycloak.sigobe;

import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;

public class DevCreateUsers extends AbstractCreateUsers {
	private static final long serialVersionUID = 1L;

	@Override
	protected void init() {
		VariableHelper.write(VariableName.KEYCLOAK_ENABLED, Boolean.TRUE);
		VariableHelper.write(VariableName.KEYCLOAK_SERVER_URL, "http://localhost:8230/auth/");
		VariableHelper.write(VariableName.KEYCLOAK_REALM_NAME, "SIIB");
		VariableHelper.write(VariableName.KEYCLOAK_CLIENT_IDENTIFIER, "admin-cli");
		VariableHelper.write(VariableName.KEYCLOAK_CLIENT_SECRET, "913c69e7-e82d-41d7-a197-d97b5c761548");
		VariableHelper.write(VariableName.KEYCLOAK_CREDENTIAL_USERNAME, "admin");
		VariableHelper.write(VariableName.KEYCLOAK_CREDENTIAL_PASSWORD, "admin");
	}
	
	@Override
	protected String getEnvironment() {
		return "dev";
	}
}