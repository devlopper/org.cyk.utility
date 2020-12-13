package org.cyk.utility.security.keycloak.server;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.junit.jupiter.api.Test;

public class RoleManagerUnitTestIntegration extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		KeycloakClientGetter.INSTANCE.set(null);
	}
	
	private void init() {
		VariableHelper.write(VariableName.KEYCLOAK_ENABLED, Boolean.TRUE);
		VariableHelper.write(VariableName.KEYCLOAK_SERVER_URL, "http://localhost:8230/auth");
		VariableHelper.write(VariableName.KEYCLOAK_REALM_NAME, "SIIB");
		VariableHelper.write(VariableName.KEYCLOAK_REALM_NAME, "Playground");
		VariableHelper.write(VariableName.KEYCLOAK_CLIENT_IDENTIFIER, "admin-cli");
		VariableHelper.write(VariableName.KEYCLOAK_CLIENT_SECRET, "913c69e7-e82d-41d7-a197-d97b5c761548");
		VariableHelper.write(VariableName.KEYCLOAK_CLIENT_SECRET, "71459049-f9a8-45a3-a026-ad07c48c3071");
		VariableHelper.write(VariableName.KEYCLOAK_CREDENTIAL_USERNAME, "admin");
		VariableHelper.write(VariableName.KEYCLOAK_CREDENTIAL_PASSWORD, "admin");
	}
	
	@Test
	public void create(){
		init();
		RoleManager.getInstance().deleteByNames("role01");
		RoleManager.getInstance().createByNames("role01");
		Role role = RoleManager.getInstance().readByName("role01");
		assertThat(role).isNotNull();
		assertThat(role.getName()).isEqualTo("role01");
		RoleManager.getInstance().deleteByNames("role01");
	}
	
	@Test
	public void list(){
		init();
		RoleManager.getInstance().deleteByNames("role01","role02");
		RoleManager.getInstance().saveByNames("role01");
		assertThat(RoleManager.getInstance().read().stream().map(Role::getName).collect(Collectors.toList())).contains("role01");
		assertThat(RoleManager.getInstance().read().stream().map(Role::getName).collect(Collectors.toList())).doesNotContain("role02");
		RoleManager.getInstance().deleteByNames("role01");
	}
	
	@Test
	public void save(){
		init();
		RoleManager.getInstance().deleteByNames("role01","role02");
		RoleManager.getInstance().saveByNames("role01");
		assertThat(RoleManager.getInstance().read().stream().map(Role::getName).collect(Collectors.toList())).contains("role01");
		assertThat(RoleManager.getInstance().read().stream().map(Role::getName).collect(Collectors.toList())).doesNotContain("role02");
		RoleManager.getInstance().saveByNames("role01","role02");
		assertThat(RoleManager.getInstance().read().stream().map(Role::getName).collect(Collectors.toList())).contains("role01","role02");
		RoleManager.getInstance().saveByNames("role01","role02");
		assertThat(RoleManager.getInstance().read().stream().map(Role::getName).collect(Collectors.toList())).contains("role01","role02");
		RoleManager.getInstance().deleteByNames("role01","role02");
	}
}