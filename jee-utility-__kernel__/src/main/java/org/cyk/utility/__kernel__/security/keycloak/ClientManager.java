package org.cyk.utility.__kernel__.security.keycloak;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.__static__.identifiable.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.keycloak.admin.client.resource.AuthorizationResource;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.PoliciesResource;
import org.keycloak.admin.client.resource.ResourcePermissionsResource;
import org.keycloak.admin.client.resource.ResourcesResource;
import org.keycloak.admin.client.resource.RolePoliciesResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.keycloak.representations.idm.authorization.ResourcePermissionRepresentation;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.keycloak.representations.idm.authorization.RolePolicyRepresentation;

import com.google.gson.Gson;

public interface ClientManager {

	Collection<Client> map(Collection<ClientRepresentation> clientRepresentations);
	Client map(ClientRepresentation clientRepresentation);
	
	Collection<Client> getByIdentifiers(Collection<String> identifiers);
	Collection<Client> getByIdentifiers(String...identifiers);
	
	void createAuthorizationPoliciesFromRolesNames(Collection<String> identifiers,Collection<String> rolesNames);
	void createAuthorizationPoliciesFromRolesNames(Collection<String> identifiers,String...rolesNames);
	
	void deleteAuthorizationPoliciesByRolesNames(Collection<String> identifiers,Collection<String> rolesNames);
	void deleteAuthorizationPoliciesByRolesNames(Collection<String> identifiers,String...rolesNames);
	
	void deleteAllAuthorizationPolicies(Collection<String> identifiers);
	void deleteAllAuthorizationPolicies(String...identifiers);
	
	void createAuthorizationResources(Collection<String> identifiers,Collection<Resource> resources);
	void createAuthorizationResources(Collection<String> identifiers,Resource...resources);
	
	void deleteAllAuthorizationResources(Collection<String> identifiers);
	void deleteAllAuthorizationResources(String...identifiers);
	
	void createAuthorizationPermissionFromRolesNamesAndResourcesNames(Collection<String> identifiers,Collection<String> rolesNames,Collection<String> resourcesNames);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements ClientManager,Serializable{
		public static Level LOGGING_LEVEL = Level.INFO;
		@Override
		public Collection<Client> map(Collection<ClientRepresentation> clientRepresentations) {
			if(CollectionHelper.isEmpty(clientRepresentations))
				return null;
			return clientRepresentations.stream().map(clientRepresentation -> map(clientRepresentation)).collect(Collectors.toList());
		}
		
		@Override
		public Client map(ClientRepresentation clientRepresentation) {
			if(clientRepresentation == null)
				return null;
			ClientResource clientResource = KeycloakHelper.getClientsResource().get(clientRepresentation.getId());
			if(clientResource == null)
				return null;
			AuthorizationResource authorizationResource = clientResource.authorization();
			Client client = new Client().set__identifier__(clientRepresentation.getId()).setIdentifier(clientRepresentation.getClientId())
					.setName(clientRepresentation.getName()).setSecured(!Boolean.TRUE.equals(clientRepresentation.isPublicClient()));
			//read permissions
			//authorizationResource.permissions().resource().;
			//if(CollectionHelper.isNotEmpty(resourceRepresentations))
			//	client.setResources(resourceRepresentations.stream().map(x -> new Resource().setIdentifier(x.getId()).setName(x.getName()).setUniformResourceIdentifiers(x.getUris()))
			//			.collect(Collectors.toList()));
			//read resources
			Collection<ResourceRepresentation> resourceRepresentations = authorizationResource.resources().resources();
			if(CollectionHelper.isNotEmpty(resourceRepresentations))
				client.setResources(resourceRepresentations.stream().map(x -> new Resource().setIdentifier(x.getId()).setName(x.getName()).setUniformResourceIdentifiers(x.getUris()))
						.collect(Collectors.toList()));
			//read policies based on roles
			PoliciesResource policiesResource = authorizationResource.policies();
			Collection<PolicyRepresentation> policyRepresentations = policiesResource.policies();
			if(CollectionHelper.isNotEmpty(policyRepresentations)) {
				for(PolicyRepresentation policyRepresentation : policyRepresentations) {
					if(POLICY_TYPE_ROLE.equals(policyRepresentation.getType())) {
						Policy policy = new Policy().setIdentifier(policyRepresentation.getId()).setName(policyRepresentation.getName());
						String roles = MapHelper.readByKey(policyRepresentation.getConfig(), POLICY_ROLES);
						if(StringHelper.isNotBlank(roles)) {
							Policy.RoleJson[] rolesJson = new Gson().fromJson(roles, Policy.RoleJson[].class);
							if(ArrayHelper.isNotEmpty(rolesJson))
								policy.setRoles(List.of(rolesJson).stream().map(x -> RoleManager.getInstance().readByIdentifier(x.id)).collect(Collectors.toList()));
						}
						client.getPolicies(Boolean.TRUE).add(policy);
					}else if(PERMISSION_TYPE_RESOURCE.equals(policyRepresentation.getType())) {
						Permission permission = new Permission().setIdentifier(policyRepresentation.getId()).setName(policyRepresentation.getName());
						client.getPermissions(Boolean.TRUE).add(permission);
					}
				}
			}
			return client;
		}
				
		@Override
		public Collection<Client> getByIdentifiers(Collection<String> identifiers) {
			if(CollectionHelper.isEmpty(identifiers))
				return null;
			Collection<ClientRepresentation> clientRepresentations = null;
			for(String identifier : identifiers) {
				ClientRepresentation clientRepresentation = CollectionHelper.getFirst(KeycloakHelper.getClientsResource().findByClientId(identifier));
				if(clientRepresentation == null) {
					LogHelper.log(String.format("Client with client-id %s not found", identifier),LOGGING_LEVEL, getClass());
					continue;
				}
				if(clientRepresentations == null)
					clientRepresentations = new ArrayList<>();
				clientRepresentations.add(clientRepresentation);
			}
			if(CollectionHelper.isEmpty(clientRepresentations))
				return null;
			return map(clientRepresentations);
		}
		
		@Override
		public Collection<Client> getByIdentifiers(String... identifiers) {
			if(ArrayHelper.isEmpty(identifiers))
				return null;
			return getByIdentifiers(CollectionHelper.listOf(identifiers));
		}
		
		private void deleteAuthorizationPoliciesByRolesNames(Collection<String> identifiers,Boolean all,Collection<String> rolesNames) {
			if(CollectionHelper.isEmpty(identifiers) || (!Boolean.TRUE.equals(all) && CollectionHelper.isEmpty(rolesNames)))
				return;
			Collection<Client> clients = getByIdentifiers(identifiers);
			if(CollectionHelper.isEmpty(clients))
				return;
			for(Client client : clients) {
				Collection<Policy> policies = client.getPolicies();
				if(CollectionHelper.isEmpty(policies))
					continue;
				ClientResource clientResource = KeycloakHelper.getClientsResource().get(client.get__identifier__());
				if(clientResource == null)
					continue;
				PoliciesResource policiesResource = clientResource.authorization().policies();
				for(Policy policy : policies) {
					if(CollectionHelper.isEmpty(policy.getRoles()))
						continue;
					for(Role role : policy.getRoles()) {
						if(Boolean.TRUE.equals(all) || rolesNames.contains(role.getName())) {
							policiesResource.policy(policy.getIdentifier()).remove();
							break;
						}
					}
				}
			}
		}
		
		@Override
		public void deleteAuthorizationPoliciesByRolesNames(Collection<String> identifiers,Collection<String> rolesNames) {
			if(CollectionHelper.isEmpty(identifiers) || CollectionHelper.isEmpty(rolesNames))
				return;
			deleteAuthorizationPoliciesByRolesNames(identifiers, null, rolesNames);
		}
		
		@Override
		public void deleteAuthorizationPoliciesByRolesNames(Collection<String> identifiers,String...rolesNames) {
			if(CollectionHelper.isEmpty(identifiers) || ArrayHelper.isEmpty(rolesNames))
				return;
			deleteAuthorizationPoliciesByRolesNames(identifiers, CollectionHelper.listOf(rolesNames));
		}
		
		@Override
		public void deleteAllAuthorizationPolicies(Collection<String> identifiers) {
			if(CollectionHelper.isEmpty(identifiers))
				return;
			deleteAuthorizationPoliciesByRolesNames(identifiers, Boolean.TRUE, null);
		}
		
		@Override
		public void deleteAllAuthorizationPolicies(String... identifiers) {
			if(ArrayHelper.isEmpty(identifiers))
				return;
			deleteAllAuthorizationPolicies(CollectionHelper.listOf(identifiers));
		}
		
		@Override
		public void createAuthorizationPoliciesFromRolesNames(Collection<String> identifiers,Collection<String> rolesNames) {
			if(CollectionHelper.isEmpty(identifiers) || CollectionHelper.isEmpty(rolesNames))
				return;
			Collection<Client> clients = getByIdentifiers(identifiers);
			if(CollectionHelper.isEmpty(clients))
				return;		
			for(Client client : clients) {
				ClientResource clientResource = KeycloakHelper.getClientsResource().get(client.get__identifier__());
				if(clientResource == null)
					continue;
				RolePoliciesResource rolePoliciesResource = clientResource.authorization().policies().role();
				for(String roleName : rolesNames) {
					String name = String.format(POLICY_NAME_HAS_ROLE_FORMAT, roleName);					
					RolePolicyRepresentation rolePolicyRepresentation = rolePoliciesResource.findByName(name);
					if(rolePolicyRepresentation != null) {
						LogHelper.log(String.format("Policy named <<%s>> NOT OVERRIDEN for role <<%s>>.",rolePolicyRepresentation.getName(),roleName), LOGGING_LEVEL, getClass());
						continue;
					}
					rolePolicyRepresentation = new RolePolicyRepresentation();
					rolePolicyRepresentation.setType(POLICY_TYPE_ROLE);
					rolePolicyRepresentation.setName(String.format(POLICY_NAME_HAS_ROLE_FORMAT, roleName));
					rolePolicyRepresentation.addRole(roleName);
					rolePoliciesResource.create(rolePolicyRepresentation);
					LogHelper.log(String.format("Policy named <<%s>> created for role <<%s>>.",rolePolicyRepresentation.getName(),roleName), LOGGING_LEVEL, getClass());
				}
			}
		}
		
		@Override
		public void createAuthorizationPoliciesFromRolesNames(Collection<String> identifiers,String...rolesNames) {
			if(CollectionHelper.isEmpty(identifiers) || ArrayHelper.isEmpty(rolesNames))
				return;
			createAuthorizationPoliciesFromRolesNames(identifiers,CollectionHelper.listOf(rolesNames));
		}
		
		@Override
		public void createAuthorizationResources(Collection<String> identifiers,Collection<Resource> resources) {
			if(CollectionHelper.isEmpty(identifiers) || CollectionHelper.isEmpty(resources))
				return;
			Collection<Client> clients = getByIdentifiers(identifiers);
			if(CollectionHelper.isEmpty(clients))
				return;		
			for(Client client : clients) {
				ClientResource clientResource = KeycloakHelper.getClientsResource().get(client.get__identifier__());
				if(clientResource == null)
					continue;
				ResourcesResource resourcesResource = clientResource.authorization().resources();
				for(Resource resource : resources) {
					if(StringHelper.isBlank(resource.getName()) || CollectionHelper.isEmpty(resource.getUniformResourceIdentifiers()))
						continue;
					String name = String.format(RESOURCE_NAME_FORMAT, resource.getName());
					ResourceRepresentation resourceRepresentation = CollectionHelper.getFirst(resourcesResource.findByName(name));
					if(resourceRepresentation != null) {
						LogHelper.log(String.format("Resource named <<%s>> NOT OVERRIDEN.",resourceRepresentation.getName()), LOGGING_LEVEL, getClass());
						continue;
					}
					resourceRepresentation = new ResourceRepresentation();
					resourceRepresentation.setName(name);
					resourceRepresentation.setDisplayName(resource.getName());
					resourceRepresentation.setUris(new LinkedHashSet<>(resource.getUniformResourceIdentifiers()));					
					resourcesResource.create(resourceRepresentation);
					LogHelper.log(String.format("Resource named <<%s>> created.",resourceRepresentation.getName()), LOGGING_LEVEL, getClass());
				}
			}
		}
		
		@Override
		public void createAuthorizationResources(Collection<String> identifiers,Resource... resources) {
			if(CollectionHelper.isEmpty(identifiers) || ArrayHelper.isEmpty(resources))
				return;
			createAuthorizationResources(identifiers, CollectionHelper.listOf(resources));
		}
		
		@Override
		public void deleteAllAuthorizationResources(Collection<String> identifiers) {
			if(CollectionHelper.isEmpty(identifiers))
				return;
			Collection<Client> clients = getByIdentifiers(identifiers);
			if(CollectionHelper.isEmpty(clients))
				return;
			for(Client client : clients) {
				Collection<Resource> resources = client.getResources();
				if(CollectionHelper.isEmpty(resources))
					continue;
				ClientResource clientResource = KeycloakHelper.getClientsResource().get(client.get__identifier__());
				if(clientResource == null)
					continue;
				ResourcesResource resourcesResource = clientResource.authorization().resources();
				for(Resource resource : resources)	
					resourcesResource.resource(resource.getIdentifier()).remove();
			}
		}
		
		@Override
		public void deleteAllAuthorizationResources(String... identifiers) {
			if(ArrayHelper.isEmpty(identifiers))
				return;
			deleteAllAuthorizationResources(CollectionHelper.listOf(identifiers));
		}
		
		@Override
		public void createAuthorizationPermissionFromRolesNamesAndResourcesNames(Collection<String> identifiers,Collection<String> rolesNames, Collection<String> resourcesNames) {
			if(CollectionHelper.isEmpty(identifiers) || CollectionHelper.isEmpty(rolesNames) || CollectionHelper.isEmpty(resourcesNames))
				return;
			Collection<Client> clients = getByIdentifiers(identifiers);
			if(CollectionHelper.isEmpty(clients))
				return;
			for(Client client : clients) {
				Collection<ResourcePermissionRepresentation> resourcePermissionRepresentations = null;
				Collection<Policy> policies = client.getPolicies();
				if(CollectionHelper.isEmpty(policies))
					continue;
				ClientResource clientResource = KeycloakHelper.getClientsResource().get(client.get__identifier__());
				if(clientResource == null)
					continue;
				ResourcePermissionsResource resourcePermissionsResource = clientResource.authorization().permissions().resource();
				for(String roleName : rolesNames) {
					for(String resourceName : resourcesNames) {
						String name = String.format(PERMISSION_NAME_OF_RESOURCE_NAME_FORMAT, resourceName);
						ResourcePermissionRepresentation resourcePermissionRepresentation = resourcePermissionsResource.findByName(name);
						if(resourcePermissionRepresentation != null) {
							LogHelper.log(String.format("Permission named <<%s>> NOT OVERRIDEN for role <<%s>> on resource <<%s>>.",name,roleName,resourceName), LOGGING_LEVEL, getClass());
							continue;
						}
						Resource resource = client.getResourceByName(resourceName);
						if(resource == null) {
							LogHelper.logWarning(String.format("resource named <<%s>> not found", resourceName), getClass());
							continue;
						}
						Policy policy = client.getPolicyByRoleName(roleName);
						if(policy == null) {
							LogHelper.logWarning(String.format("policy for role <<%s>> not found", roleName), getClass());
							continue;
						}
						resourcePermissionRepresentation = new ResourcePermissionRepresentation();
						resourcePermissionRepresentation.setName(name);
						resourcePermissionRepresentation.addResource(resource.getIdentifier());
						resourcePermissionRepresentation.addPolicy(policy.getIdentifier());
						
						if(resourcePermissionRepresentations == null)
							resourcePermissionRepresentations = new ArrayList<>();
						resourcePermissionRepresentations.add(resourcePermissionRepresentation);
					}
				}
				if(CollectionHelper.isEmpty(resourcePermissionRepresentations))
					continue;
				resourcePermissionRepresentations.forEach(resourcePermissionRepresentation -> {
					resourcePermissionsResource.create(resourcePermissionRepresentation);
				});
			}
		}
		
		public static String POLICY_NAME_HAS_ROLE_FORMAT = "Has role %s policy";
		private static final String POLICY_TYPE_ROLE = "role";
		private static final String POLICY_ROLES = "roles";
		
		public static String PERMISSION_NAME_OF_RESOURCE_NAME_FORMAT = "View resource %s permission";
		private static final String PERMISSION_TYPE_RESOURCE = "resource";
		
		public static String RESOURCE_NAME_FORMAT = "%s";
	}
	
	/**/
	
	
	/**/
	
	static ClientManager getInstance() {
		return Helper.getInstance(ClientManager.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}