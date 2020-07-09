package org.cyk.utility.__kernel__.security.keycloak;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface UserManager {

	UserManager create(Collection<User> users);
	
	default UserManager create(User...users) {
		if(ArrayHelper.isEmpty(users))
			return null;
		return create(CollectionHelper.listOf(users));
	}
	
	Collection<User> read(Arguments arguments);
	
	Collection<User> readFrom(Integer firstElementIndex,Integer numberOfElements);
	
	Collection<User> readAll();
	
	Collection<String> readAllNames();
	
	default User readByUserName(String userName) {
		if(StringHelper.isBlank(userName))
			return null;
		return CollectionHelper.getFirst(read(new Arguments().setNames(List.of(userName))));
	}
	
	UserManager update(Collection<User> users,Collection<Property> properties);
	
	default UserManager update(Collection<Property> properties,User...users) {
		if(ArrayHelper.isEmpty(users))
			return null;
		return update(CollectionHelper.listOf(users),properties);
	}
	
	default UserManager updateRoles(Collection<User> users) {
		if(CollectionHelper.isEmpty(users))
			throw new IllegalArgumentException("users are required");
		return update(users, List.of(Property.ROLES));
	}
	
	default UserManager updateRoles(User...users) {
		if(ArrayHelper.isEmpty(users))
			return null;
		return update(CollectionHelper.listOf(users),List.of(Property.ROLES));
	}
	
	UserManager delete(Collection<String> usersNames);
	
	default UserManager delete(String...usersNames) {
		if(ArrayHelper.isEmpty(usersNames))
			return this;
		delete(CollectionHelper.listOf(usersNames));
		return this;
	}
	
	public static abstract class AbstractImpl extends AbstractObject implements UserManager,Serializable {
		
		public static String ADMINISTRATOR_USERNAME = "admin";
		
		@Override
		public UserManager create(Collection<User> users) {
			if(CollectionHelper.isEmpty(users))
				throw new IllegalArgumentException("users are required");
			users.forEach(user -> {
				__create__(KeycloakHelper.getUsersResource(), KeycloakHelper.getRolesResource(), user);
			});
			return this;
		}
		
		protected void __create__(UsersResource usersResource,RolesResource rolesResource,User user) {
			UserRepresentation userRepresentation = new UserRepresentation();
			userRepresentation.setEnabled(Boolean.TRUE);
			userRepresentation.setUsername(user.getName());
			userRepresentation.setFirstName(user.getFirstName());
			userRepresentation.setLastName(user.getLastNames());
			userRepresentation.setEmail(user.getElectronicMailAddress());
			
			Response response;
			try {
				response = usersResource.create(userRepresentation);
				LogHelper.logInfo("User has been created in keycloak. username = "+userRepresentation.getUsername(), getClass());
			} catch (Exception exception) {
				throw new RuntimeException("cannot create user "+user+" because "+exception.getMessage(),exception);
			}
			String identifier = response.getLocation() == null || StringHelper.isBlank(response.getLocation().getPath()) ? null : response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
			if(StringHelper.isBlank(identifier))
				throw new RuntimeException("cannot create user "+user+" because duplicate user");
			UserResource userResource = usersResource.get(identifier);			
			CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
			credentialRepresentation.setTemporary(Boolean.TRUE);
			credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
			credentialRepresentation.setValue(ValueHelper.defaultToIfNull(user.getPass(), "123"));
			userResource.resetPassword(credentialRepresentation);							
			if(CollectionHelper.isNotEmpty(user.getRoles()))
				__addRoles__(userResource, rolesResource, user);
		}
		
		protected void __addRoles__(UserResource userResource,RolesResource rolesResource,User user) {
			List<RoleRepresentation> roleRepresentations = new ArrayList<>();
			for(Role index : user.getRoles()) {
				roleRepresentations.add(rolesResource.get(index.getName()).toRepresentation());
			}
			userResource.roles().realmLevel().add(roleRepresentations);		
		}
		
		@Override
		public Collection<User> read(Arguments arguments) {
			UsersResource usersResource = KeycloakHelper.getUsersResource();
			Collection<UserRepresentation> userRepresentations = null;
			if(userRepresentations == null && CollectionHelper.isNotEmpty(arguments.identifiers)) {
				UserRepresentation userRepresentation = usersResource.get(CollectionHelper.getFirst(arguments.identifiers)).toRepresentation();
				if(userRepresentation != null)
					userRepresentations = CollectionHelper.listOf(userRepresentation);
			}
			if(userRepresentations == null && CollectionHelper.isNotEmpty(arguments.names)) {
				userRepresentations = usersResource.search(CollectionHelper.getFirst(arguments.names));
			}
			if(userRepresentations == null && arguments.numberOfElements != null) {
				Integer from = ValueHelper.defaultToIfNull(arguments.firstElementIndex, 0);
				Integer to = from + (arguments.numberOfElements < 0 ? 0 : arguments.numberOfElements);
				userRepresentations = usersResource.list(from,to);
			}
			if(userRepresentations == null) {
				userRepresentations = usersResource.list();
			}
			/*
			if(CollectionHelper.isEmpty(arguments.names))
				userRepresentations = usersResource.list();
			else
				userRepresentations = usersResource.search(CollectionHelper.getFirst(arguments.names));
			*/			
			if(CollectionHelper.isEmpty(userRepresentations))
				return null;
			userRepresentations = userRepresentations.stream().filter(userRepresentation -> !userRepresentation.getUsername().equals(ADMINISTRATOR_USERNAME)).collect(Collectors.toList());
			Collection<User> users = null;
			for(UserRepresentation userRepresentation : userRepresentations) {
				User user = new User()
						.setIdentifier(userRepresentation.getId())
						.setName(userRepresentation.getUsername())
						.setFirstName(userRepresentation.getFirstName())
						.setLastNames(userRepresentation.getLastName())
						.setElectronicMailAddress(userRepresentation.getEmail())
						.setRoles(RoleManager.getInstance().map(usersResource.get(userRepresentation.getId()).roles().getAll().getRealmMappings()))
						;
				if(users == null)
					users = new ArrayList<User>();
				users.add(user);
			}
			return users;
		}
		
		@Override
		public Collection<User> readFrom(Integer firstElementIndex, Integer numberOfElements) {
			return read(new Arguments().setFirstElementIndex(firstElementIndex).setNumberOfElements(numberOfElements));
		}
		
		@Override
		public Collection<User> readAll() {
			return read(new Arguments());
		}
		
		@Override
		public Collection<String> readAllNames() {
			Collection<User> users = readAll();
			if(CollectionHelper.isEmpty(users))
				return null;
			return users.stream().map(user -> user.getName()).collect(Collectors.toSet());
		}
		
		@Override
		public UserManager update(Collection<User> users,Collection<Property> properties) {
			if(CollectionHelper.isEmpty(users) || CollectionHelper.isEmpty(properties))
				return this;
			if(properties.contains(Property.ROLES)) {
				RolesResource rolesResource = KeycloakHelper.getRolesResource();
				UsersResource usersResource = KeycloakHelper.getUsersResource();			
				users.forEach(user -> {
					UserResource userResource = usersResource.get(readByUserName(user.getName()).getIdentifier());
					//remove existing
					userResource.roles().realmLevel().remove(userResource.roles().getAll().getRealmMappings());					
					//create current and hidden
					if(CollectionHelper.isNotEmpty(RoleManager.HIDDEN))
						user.getRoles(Boolean.TRUE).addAll(RoleManager.HIDDEN.stream().map(role -> new Role(role)).collect(Collectors.toList()));
					if(CollectionHelper.isNotEmpty(user.getRoles()))
						__addRoles__(userResource, rolesResource, user);
				});
			}			
			return this;
		}
			
		@Override
		public UserManager delete(Collection<String> usersNames) {
			if(CollectionHelper.isEmpty(usersNames))
				return this;
			UsersResource usersResource = KeycloakHelper.getUsersResource();
			usersNames.forEach(userName -> {
				UserRepresentation userRepresentation = CollectionHelper.getFirst(usersResource.search(userName));
				if(userRepresentation != null)
					usersResource.delete(userRepresentation.getId());
			});
			return this;
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments extends AbstractObject implements Serializable {
		private Collection<User> users;
		private Collection<String> identifiers,names;
		private Collection<String> electronicMailAddresses;
		private Integer firstElementIndex,numberOfElements;
	}
	
	/**/
	
	static UserManager getInstance() {
		return Helper.getInstance(UserManager.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	public static enum Property {
		ROLES
	}
}