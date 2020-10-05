package org.cyk.utility.__kernel__.security.keycloak;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import javax.ws.rs.NotFoundException;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.__static__.identifiable.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.RoleRepresentation;

public interface RoleManager {
	
	void create(Collection<Role> roles);
	void create(Role...roles);
	
	void createByNames(Collection<String> names);
	void createByNames(String...names);
	
	void delete(Collection<Role> roles);
	void delete(Role...roles);
	
	void deleteByNames(Collection<String> names);
	void deleteByNames(String...names);
	
	void save(Collection<Role> roles);
	void save(Role...roles);
	
	void saveByNames(Collection<String> names);
	void saveByNames(String...names);
	
	Collection<Role> read();
	Role readByName(String name);
	Collection<Role> readByNames(Collection<String> names);
	Collection<Role> readByNames(String...names);
	
	Role readByIdentifier(String identifier);
	Collection<Role> readByIdentifiers(Collection<String> identifiers);
	Collection<Role> readByIdentifiers(String...identifiers);
	
	Collection<Role> map(Collection<RoleRepresentation> roleRepresentations);
	Role map(RoleRepresentation roleRepresentation);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements RoleManager,Serializable{
		public static Level LOGGING_LEVEL = Level.FINE;
		@Override
		public void create(Collection<Role> roles) {
			if(CollectionHelper.isEmpty(roles))
				return;
			for(Role role : roles) {
				RoleRepresentation roleRepresentation = new RoleRepresentation();
				roleRepresentation.setName(role.getName());
				KeycloakHelper.getRolesResource().create(roleRepresentation);
				LogHelper.log(String.format("Role named <<%s>> has been created.",roleRepresentation.getName()), LOGGING_LEVEL, getClass());
			}		
		}
		
		@Override
		public void create(Role... roles) {
			if(ArrayHelper.isEmpty(roles))
				return;
			create(CollectionHelper.listOf(roles));
		}
		
		@Override
		public void createByNames(Collection<String> names) {
			if(CollectionHelper.isEmpty(names))
				return;
			create(names.stream().map(x -> new Role().setName(x)).collect(Collectors.toList()));
		}
		
		@Override
		public void createByNames(String... names) {
			if(ArrayHelper.isEmpty(names))
				return;
			createByNames(CollectionHelper.listOf(names));
		}
		
		@Override
		public void save(Collection<Role> roles) {
			if(CollectionHelper.isEmpty(roles))
				return;
			Collection<Role> existing = read();
			if(CollectionHelper.isNotEmpty(existing)) {
				Collection<String> names = existing.stream().map(x -> x.getName()).collect(Collectors.toList());
				roles = roles.stream().filter(x -> !names.contains(x.getName())).collect(Collectors.toList());
			}
			if(CollectionHelper.isEmpty(roles))
				return;
			create(roles);
		}
		
		@Override
		public void save(Role... roles) {
			if(ArrayHelper.isEmpty(roles))
				return;
			save(CollectionHelper.listOf(roles));
		}
		
		@Override
		public void saveByNames(Collection<String> names) {
			if(CollectionHelper.isEmpty(names))
				return;
			save(names.stream().map(x -> new Role().setName(x)).collect(Collectors.toList()));
		}
		
		@Override
		public void saveByNames(String... names) {
			if(ArrayHelper.isEmpty(names))
				return;
			saveByNames(CollectionHelper.listOf(names));
		}
		
		@Override
		public void delete(Collection<Role> roles) {
			if(CollectionHelper.isEmpty(roles))
				return;
			Collection<Role> existing = read();
			if(CollectionHelper.isEmpty(existing))
				return;		
			Collection<String> names = existing.stream().map(x -> x.getName()).collect(Collectors.toList());
			roles = roles.stream().filter(x -> names.contains(x.getName())).collect(Collectors.toList());			
			if(CollectionHelper.isEmpty(roles))
				return;
			for(Role role : roles)
				try {
					KeycloakHelper.getRolesResource().deleteRole(role.getName());
					LogHelper.log(String.format("Role named <<%s>> has been deleted.",role.getName()), LOGGING_LEVEL, getClass());
				} catch (NotFoundException exception) {}
		}
		
		@Override
		public void delete(Role... roles) {
			if(ArrayHelper.isEmpty(roles))
				return;
			delete(CollectionHelper.listOf(roles));
		}
		
		@Override
		public void deleteByNames(Collection<String> names) {
			if(CollectionHelper.isEmpty(names))
				return;
			delete(names.stream().map(x -> new Role().setName(x)).collect(Collectors.toList()));
		}
		
		@Override
		public void deleteByNames(String... names) {
			if(ArrayHelper.isEmpty(names))
				return;
			deleteByNames(CollectionHelper.listOf(names));
		}
		
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
		
		@Override
		public Role readByName(String name) {
			if(StringHelper.isBlank(name))
				return null;
			RolesResource rolesResource = KeycloakHelper.getRolesResource();
			if(rolesResource == null)
				throw new RuntimeException("keycloak roles resource not found");
			RoleRepresentation roleRepresentation;
			try {
				roleRepresentation = rolesResource.get(name).toRepresentation();
			} catch (NotFoundException exception) {
				return null;
			}
			if(roleRepresentation == null)
				return null;
			return map(roleRepresentation);
		}
		
		@Override
		public Collection<Role> readByNames(Collection<String> names) {
			if(CollectionHelper.isEmpty(names))
				return null;
			Collection<Role> roles = new ArrayList<>();
			for(String name : names) {
				Role role = readByName(name);
				if(role == null)
					continue;
				roles.add(role);
			}
			return roles;
		}
		
		@Override
		public Collection<Role> readByNames(String... names) {
			if(ArrayHelper.isEmpty(names))
				return null;
			return readByNames(CollectionHelper.listOf(names));
		}
		
		@Override
		public Role readByIdentifier(String identifier) {
			if(StringHelper.isBlank(identifier))
				return null;
			RolesResource rolesResource = KeycloakHelper.getRolesResource();
			if(rolesResource == null)
				throw new RuntimeException("keycloak roles resource not found");
			Collection<RoleRepresentation> roleRepresentations = rolesResource.list();
			if(CollectionHelper.isEmpty(roleRepresentations))
				return null;			
			RoleRepresentation roleRepresentation = CollectionHelper.getFirst(roleRepresentations.stream().filter(x -> x.getId().equals(identifier)).collect(Collectors.toList()));
			if(roleRepresentation == null)
				return null;
			return map(roleRepresentation);
		}
		
		@Override
		public Collection<Role> readByIdentifiers(Collection<String> identifiers) {
			if(CollectionHelper.isEmpty(identifiers))
				return null;
			Collection<Role> roles = new ArrayList<>();
			for(String identifier : identifiers) {
				Role role = readByIdentifier(identifier);
				if(role == null)
					continue;
				roles.add(role);
			}
			return roles;
		}
		
		@Override
		public Collection<Role> readByIdentifiers(String... identifiers) {
			if(ArrayHelper.isEmpty(identifiers))
				return null;
			return readByIdentifiers(CollectionHelper.listOf(identifiers));
		}
		
		@Override
		public Role map(RoleRepresentation roleRepresentation) {
			if(roleRepresentation == null)
				return null;
			return new Role().setIdentifier(roleRepresentation.getId()).setName(roleRepresentation.getName());
		}
		
		@Override
		public Collection<Role> map(Collection<RoleRepresentation> roleRepresentations) {
			if(CollectionHelper.isEmpty(roleRepresentations))
				return null;
			roleRepresentations = roleRepresentations.stream().filter(roleRepresentation -> !HIDDEN.contains(roleRepresentation.getName())).collect(Collectors.toList());
			return roleRepresentations.stream().map(roleRepresentation -> map(roleRepresentation)).collect(Collectors.toList());
		}
	}
	
	/**/
	
	static RoleManager getInstance() {
		return Helper.getInstance(RoleManager.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	Collection<String> HIDDEN = new HashSet<>(List.of("offline_access","uma_authorization"));
	Collection<String> DEFAULT = new ArrayList<>();
}