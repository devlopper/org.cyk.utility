package org.cyk.utility.security.keycloak.server.sigobe;

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
