package org.cyk.utility.security.keycloak.server;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.cyk.utility.security.keycloak.server.UserManager.Arguments;
import org.cyk.utility.security.keycloak.server.UserManager.Property;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.junit.jupiter.api.Test;

public class UserManagerUnitTestIntegrationSIIBDev extends AbstractWeldUnitTest {
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
		VariableHelper.write(VariableName.KEYCLOAK_CLIENT_IDENTIFIER, "admin-cli");
		VariableHelper.write(VariableName.KEYCLOAK_CLIENT_SECRET, "ebe335e9-c747-46f0-91f2-929325132782");
		VariableHelper.write(VariableName.KEYCLOAK_CREDENTIAL_USERNAME, "admin");
		VariableHelper.write(VariableName.KEYCLOAK_CREDENTIAL_PASSWORD, "admin");
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
		assertThat(UserManager.getInstance().read(new Arguments().setNumberOfElements(Integer.MAX_VALUE)).stream().map(User::getName).collect(Collectors.toList())).contains("user01");
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
	
	@Test
	public void addAndDeleteRolesByNames(){
		init();
		RoleManager.getInstance().deleteByNames("role01","role02");
		RoleManager.getInstance().createByNames("role01","role02");		
		UserManager.getInstance().delete("user01");
		
		UserManager.getInstance().create(new User().setName("user01").setElectronicMailAddress("test@mail.com").setFirstName("komenan").setLastNames("yao christian"));
		User user = UserManager.getInstance().readByUserName("user01");
		assertThat(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())).doesNotContain("role01","role02");
		
		UserManager.getInstance().deleteRolesByNames(List.of(UserManager.getInstance().readByUserName("user01")), "role01","role02");
		
		UserManager.getInstance().addRolesByNames(List.of(UserManager.getInstance().readByUserName("user01")), "role01");
		user = UserManager.getInstance().readByUserName("user01");
		assertThat(user.getRoles()).isNotNull();
		assertThat(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())).contains("role01");
		assertThat(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())).doesNotContain("role02");
		
		UserManager.getInstance().addRolesByNames(List.of(UserManager.getInstance().readByUserName("user01")), "role01");
		user = UserManager.getInstance().readByUserName("user01");
		assertThat(user.getRoles()).isNotNull();
		assertThat(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())).contains("role01");
		assertThat(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())).doesNotContain("role02");
		
		UserManager.getInstance().addRolesByNames(List.of(UserManager.getInstance().readByUserName("user01")), "role02");
		user = UserManager.getInstance().readByUserName("user01");
		assertThat(user.getRoles()).isNotNull();
		assertThat(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())).contains("role01","role02");
		
		UserManager.getInstance().deleteRolesByNames(List.of(UserManager.getInstance().readByUserName("user01")), "role01");
		
		user = UserManager.getInstance().readByUserName("user01");
		assertThat(user.getRoles()).isNotNull();
		assertThat(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())).contains("role02");
		
		UserManager.getInstance().deleteRolesByNames(List.of(UserManager.getInstance().readByUserName("user01")), "role02");
		
		user = UserManager.getInstance().readByUserName("user01");
		assertThat(user.getRoles()).isNotNull();
		assertThat(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())).isEmpty();
		
		UserManager.getInstance().deleteRolesByNames(List.of(UserManager.getInstance().readByUserName("user01")), "role02");
		
		user = UserManager.getInstance().readByUserName("user01");
		assertThat(user.getRoles()).isNotNull();
		assertThat(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())).isEmpty();
		
		UserManager.getInstance().delete("user01");
		RoleManager.getInstance().deleteByNames("role01","role02");
	}
	
	@Test
	public void updateFirstNameLastNamesElectronicMail(){
		init();
		UserManager.getInstance().delete("user_to_update");
		
		UserManager.getInstance().create(new User().setName("user_to_update").setElectronicMailAddress("user_to_update@mail.com").setFirstName("komenan").setLastNames("yao christian"));
		User user = UserManager.getInstance().readByUserName("user_to_update");
		assertThat(user.getFirstName()).isEqualTo("komenan");
		assertThat(user.getLastNames()).isEqualTo("yao christian");
		assertThat(user.getElectronicMailAddress()).isEqualTo("user_to_update@mail.com");
		
		user.setElectronicMailAddress("user_to_updatenew@mail.com");
		user.setFirstName("Zadi");
		user.setLastNames("Konan");
		UserManager.getInstance().update(List.of(user), List.of(UserManager.Property.ELECTRONIC_MAIL_ADDRESS,UserManager.Property.FIRST_NAME,UserManager.Property.LAST_NAMES));
		
		user = UserManager.getInstance().readByUserName("user_to_update");
		assertThat(user.getFirstName()).isEqualTo("Zadi");
		assertThat(user.getLastNames()).isEqualTo("Konan");
		assertThat(user.getElectronicMailAddress()).isEqualTo("user_to_updatenew@mail.com");
		
		UserManager.getInstance().delete("user_to_update");
	}
}