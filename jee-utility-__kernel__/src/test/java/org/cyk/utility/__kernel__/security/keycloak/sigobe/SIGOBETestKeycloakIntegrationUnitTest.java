package org.cyk.utility.__kernel__.security.keycloak.sigobe;

public class SIGOBETestKeycloakIntegrationUnitTest extends AbstractSIGOBEKeycloakIntegrationUnitTest {

	@Override
	protected String getPersistenceUnitName() {
		return "sigobe_test";
	}
	
	@Override
	protected String getRealm() {
		return "SIIBTEST";
	}
}
