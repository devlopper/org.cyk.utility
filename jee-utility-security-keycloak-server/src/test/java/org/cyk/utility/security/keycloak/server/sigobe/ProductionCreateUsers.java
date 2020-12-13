package org.cyk.utility.security.keycloak.server.sigobe;

public class ProductionCreateUsers extends AbstractCreateUsers {
	private static final long serialVersionUID = 1L;

	@Override
	protected String getEnvironment() {
		return "prod";
	}
}