package org.cyk.utility.security.keycloak.server.sigobe;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.cyk.utility.security.keycloak.server.KeycloakClientGetter;
import org.cyk.utility.security.keycloak.server.Role;
import org.cyk.utility.security.keycloak.server.User;
import org.cyk.utility.security.keycloak.server.UserManager;
import org.cyk.utility.security.keycloak.server.UserManager.Arguments;
import org.cyk.utility.security.keycloak.server.UserManager.Property;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.junit.jupiter.api.Test;

public class UserManagerUnitTestIntegrationSIGOBE extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		KeycloakClientGetter.INSTANCE.set(null);
	}
	
	private void init() {
		VariableHelper.write(VariableName.KEYCLOAK_ENABLED, Boolean.TRUE);
		VariableHelper.write(VariableName.KEYCLOAK_SERVER_URL, "http://10.3.4.60:8080/auth/");
		VariableHelper.write(VariableName.KEYCLOAK_REALM_NAME, "SIIBTEST");
		VariableHelper.write(VariableName.KEYCLOAK_CLIENT_IDENTIFIER, "admin-cli");
		VariableHelper.write(VariableName.KEYCLOAK_CLIENT_SECRET, "ac0749e3-14f8-4a14-a6e1-4edfb9375e69");
		VariableHelper.write(VariableName.KEYCLOAK_CREDENTIAL_USERNAME, "mic-acteur-api");
		VariableHelper.write(VariableName.KEYCLOAK_CREDENTIAL_PASSWORD, "mic-@cteur-@pi@2O2o");
	}
	
	@Test
	public void create(){
		init();
		UserManager.getInstance().delete("user01");
		UserManager.getInstance().create(new User().setName("user01").setElectronicMailAddress("test@mail.com").setFirstName("komenan").setLastNames("yao christian"));
		User user = UserManager.getInstance().readByUserName("user01");
		assertThat(user).isNotNull();
		assertThat(user.getName()).isEqualTo("user01");
		assertThat(user.getElectronicMailAddress()).isEqualTo("test@mail.com");
		assertThat(user.getFirstName()).isEqualTo("komenan");
		assertThat(user.getLastNames()).isEqualTo("yao christian");
		UserManager.getInstance().delete("user01");
	}
	
	@Test
	public void list(){
		init();
		UserManager.getInstance().delete("user01","user02");
		UserManager.getInstance().create(new User().setName("user01"));
		assertThat(UserManager.getInstance().read(new Arguments()).stream().map(User::getName).collect(Collectors.toList())).contains("user01");
		UserManager.getInstance().delete("user01","user02");
	}
	
	@Test
	public void update(){
		init();
		UserManager.getInstance().delete("user01");
		UserManager.getInstance().create(new User().setName("user01").setElectronicMailAddress("test@mail.com").setFirstName("komenan").setLastNames("yao christian"));
		User user = UserManager.getInstance().readByUserName("user01");
		assertThat(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())).doesNotContain("REQUERANT");
		assertThat(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())).doesNotContain("AGENT_TRAITANT");
		
		user.addRoles(new Role("REQUERANT"));
		UserManager.getInstance().update(List.of(Property.ROLES),user);
		
		user = UserManager.getInstance().readByUserName("user01");
		assertThat(user.getRoles()).isNotNull();
		assertThat(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())).contains("REQUERANT");
		assertThat(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())).doesNotContain("AGENT_TRAITANT");
		
		user.getRoles().clear();
		user.addRoles(new Role("AGENT_TRAITANT"));
		UserManager.getInstance().update(List.of(Property.ROLES),user);
		
		user = UserManager.getInstance().readByUserName("user01");
		assertThat(user.getRoles()).isNotNull();
		assertThat(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())).contains("AGENT_TRAITANT");
		assertThat(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())).doesNotContain("REQUERANT");
		
		user.getRoles().clear();
		user.addRoles(new Role("ADMINISTRATEUR"),new Role("AGENT_TRAITANT"),new Role("COLLABORATEUR"),new Role("GESTIONNAIRE_ETAT"),new Role("GESTIONNAIRE_REPONSE")
				,new Role("REQUERANT"),new Role("RESPONSABLE"));
		UserManager.getInstance().update(List.of(Property.ROLES),user);
		
		user = UserManager.getInstance().readByUserName("user01");
		assertThat(user.getRoles()).isNotNull();
		assertThat(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())).contains("ADMINISTRATEUR","AGENT_TRAITANT","COLLABORATEUR","GESTIONNAIRE_ETAT"
				,"GESTIONNAIRE_REPONSE","REQUERANT","RESPONSABLE");
		
		UserManager.getInstance().delete("user01");
	}
}