package org.cyk.utility.__kernel__.security.keycloak;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.junit.jupiter.api.Test;

public class KeycloakHelperUnitTestIntegration extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	private void init() {
		VariableHelper.write(VariableName.KEYCLOAK_ENABLED, Boolean.TRUE);
		VariableHelper.write(VariableName.KEYCLOAK_SERVER_URL, "http://localhost:8230/auth");
		VariableHelper.write(VariableName.KEYCLOAK_REALM_NAME, "Playground");
		VariableHelper.write(VariableName.KEYCLOAK_CLIENT_IDENTIFIER, "admin-cli");
		VariableHelper.write(VariableName.KEYCLOAK_CLIENT_SECRET, "71459049-f9a8-45a3-a026-ad07c48c3071");
		VariableHelper.write(VariableName.KEYCLOAK_CREDENTIAL_USERNAME, "admin");
		VariableHelper.write(VariableName.KEYCLOAK_CREDENTIAL_PASSWORD, "admin");
	}
	
	@Test
	public void executeActionEmailUpdatePassword(){
		init();	
		KeycloakHelper.executeActionEmailUpdatePassword("6191fa8d-fbec-45ae-9c80-0541b51b8009");
	}	
}