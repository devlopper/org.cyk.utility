package org.cyk.utility.__kernel__.security.keycloak;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.__static__.identifiable.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.RoleRepresentation;

public interface RoleManager {
	
	Collection<Role> read();
	
	default Collection<Role> map(Collection<RoleRepresentation> roleRepresentations) {
		if(CollectionHelper.isEmpty(roleRepresentations))
			return null;
		roleRepresentations = roleRepresentations.stream().filter(roleRepresentation -> !HIDDEN.contains(roleRepresentation.getName())).collect(Collectors.toList());
		return roleRepresentations.stream().map(roleRepresentation -> new Role().setIdentifier(roleRepresentation.getId()).setName(roleRepresentation.getName())).collect(Collectors.toList());
	}
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements RoleManager,Serializable{		
		@Override
		public Collection<Role> read() {
			RolesResource rolesResource = KeycloakHelper.getRolesResource();
			if(rolesResource == null)
				throw new RuntimeException("keycloak roles resource not found");
			Collection<RoleRepresentation> roleRepresentations = rolesResource.list();
			if(CollectionHelper.isEmpty(roleRepresentations))
				return null;
			return map(roleRepresentations);
		}
	}
	
	/**/
	
	static RoleManager getInstance() {
		return Helper.getInstance(RoleManager.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
	Collection<String> HIDDEN = new HashSet<>(List.of("offline_access","uma_authorization"));
}