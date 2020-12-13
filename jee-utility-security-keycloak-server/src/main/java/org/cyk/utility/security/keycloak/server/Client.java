package org.cyk.utility.security.keycloak.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class Client implements Serializable {

	private String __identifier__;
	private String identifier;
	private String name;
	private Boolean secured;
	private Collection<Permission> permissions;
	private Collection<Resource> resources;
	private Collection<Policy> policies;
	
	public Client(String name) {
		this.name = name;
	}
	
	public Collection<Permission> getPermissions(Boolean injectIfNull) {
		if(permissions == null && Boolean.TRUE.equals(injectIfNull))
			permissions = new ArrayList<>();
		return permissions;
	}
	
	public Collection<Policy> getPolicies(Boolean injectIfNull) {
		if(policies == null && Boolean.TRUE.equals(injectIfNull))
			policies = new ArrayList<>();
		return policies;
	}
	
	public Policy getPolicyByRoleName(String roleName) {
		if(CollectionHelper.isEmpty(policies))
			return null;
		for(Policy policy : policies)
			if(Boolean.TRUE.equals(policy.hasRoleName(roleName)))
				return policy;
		return null;
	}
	
	public Collection<Resource> getResources(Boolean injectIfNull) {
		if(resources == null && Boolean.TRUE.equals(injectIfNull))
			resources = new ArrayList<>();
		return resources;
	}
	
	public Resource getResourceByName(String name) {
		if(CollectionHelper.isEmpty(resources))
			return null;
		for(Resource resource : resources)
			if(resource.getName().equals(name))
				return resource;
		return null;
	}
	
	public Collection<Role> getPoliciesRoles() {
		if(CollectionHelper.isEmpty(policies))
			return null;
		Collection<Role> roles = null;
		for(Policy policy : policies) {
			if(CollectionHelper.isEmpty(policy.getRoles()))
				continue;
			if(roles == null)
				roles = new ArrayList<>();
			roles.addAll(policy.getRoles());
		}
		return roles;
	}
	
	public Collection<String> getResourcesUniformIdentifiers() {
		if(CollectionHelper.isEmpty(resources))
			return null;
		Collection<String> uniformIdentifiers = null;
		for(Resource resource : resources) {
			if(CollectionHelper.isEmpty(resource.getUniformResourceIdentifiers()))
				continue;
			if(uniformIdentifiers == null)
				uniformIdentifiers = new ArrayList<>();
			uniformIdentifiers.addAll(resource.getUniformResourceIdentifiers());
		}
		return uniformIdentifiers;
	}
	
	@Override
	public String toString() {
		return identifier;
	}
}