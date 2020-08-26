package org.cyk.utility.__kernel__.security.keycloak.sigobe;

public class ProductionCreateUsers extends AbstractCreateUsers {
	private static final long serialVersionUID = 1L;

	@Override
	protected String getEnvironment() {
		return "prod";
	}
}