package org.cyk.utility.__kernel__.security.keycloak;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.junit.jupiter.api.Test;

public class ClientManagerUnitTestIntegration extends AbstractWeldUnitTest {
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
	public void list(){
		init();
		Collection<Client> clients = ClientManager.getInstance().getByIdentifiers("application01_02");
		assertThat(clients.stream().map(Client::getIdentifier).collect(Collectors.toList())).contains("application01_02");
	}
	
	@Test
	public void create_delete_policies(){
		init();
		ClientManager.getInstance().deleteAuthorizationPoliciesByRolesNames(List.of("application01_02"), "r01","r02","r03","r04");
		Client client = CollectionHelper.getFirst(ClientManager.getInstance().getByIdentifiers("application01_02"));
		assertThat(client.getPoliciesRoles()).isNull();
		
		ClientManager.getInstance().createAuthorizationPoliciesFromRolesNames(List.of("application01_02"), "r01");
		client = CollectionHelper.getFirst(ClientManager.getInstance().getByIdentifiers("application01_02"));
		assertThat(client.getPoliciesRoles()).as("policies roles not found").isNotNull();
		assertThat(client.getPoliciesRoles().stream().map(x->x.getName()).collect(Collectors.toList())).contains("r01");
		
		ClientManager.getInstance().deleteAuthorizationPoliciesByRolesNames(List.of("application01_02"), "r01","r02","r03","r04");
	}
	
	@Test
	public void delete_create_resources(){
		init();
		ClientManager.getInstance().deleteAllAuthorizationResources("application01_02");
		Client client = CollectionHelper.getFirst(ClientManager.getInstance().getByIdentifiers("application01_02"));
		assertThat(client.getResourcesUniformIdentifiers()).isNull();
		
		ClientManager.getInstance().createAuthorizationResources(List.of("application01_02"), List.of(new Resource().setName("Espace privee")
				.addUniformResourceIdentifiers("/*")));
		client = CollectionHelper.getFirst(ClientManager.getInstance().getByIdentifiers("application01_02"));
		assertThat(client.getResourcesUniformIdentifiers()).as("URIs not found").isNotNull();
		assertThat(client.getResourcesUniformIdentifiers()).contains("/*");
		
		ClientManager.getInstance().deleteAllAuthorizationResources("application01_02");
	}
}