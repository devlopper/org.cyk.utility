package org.cyk.utility.security.keycloak.server;

import java.io.Serializable;

import javax.ws.rs.ProcessingException;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface KeycloakClientGetter {

	Keycloak get();
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements KeycloakClientGetter,Serializable {
		private static final long serialVersionUID = 1L;

		public static String VARIABLE_NAME_KEYCLOAK_SERVER_URL = VariableName.KEYCLOAK_SERVER_URL;
		public static String VARIABLE_NAME_KEYCLOAK_REALM_NAME = VariableName.KEYCLOAK_REALM_NAME;
		public static String VARIABLE_NAME_KEYCLOAK_CLIENT_IDENTIFIER = VariableName.KEYCLOAK_CLIENT_IDENTIFIER;
		public static String VARIABLE_NAME_KEYCLOAK_CLIENT_SECRET = VariableName.KEYCLOAK_CLIENT_SECRET;
		public static String VARIABLE_NAME_KEYCLOAK_CREDENTIAL_USERNAME = VariableName.KEYCLOAK_CREDENTIAL_USERNAME;
		public static String VARIABLE_NAME_KEYCLOAK_CREDENTIAL_PASSWORD = VariableName.KEYCLOAK_CREDENTIAL_PASSWORD;
		
		public static Integer CONNECTION_POOL_SIZE = null;
		
		public Keycloak get() {
			if(CLIENT.isHasBeenSet())
				return (Keycloak) CLIENT.get();
			if(!ConfigurationHelper.is(VariableName.KEYCLOAK_ENABLED)) {
				CLIENT.set(null);
				LogHelper.logInfo("keycloak is not enabled", KeycloakClientGetter.class);
				return null;
			}
			CustomJacksonProvider customJacksonProvider = new CustomJacksonProvider();
			ResteasyClientBuilder resteasyClientBuilder = new ResteasyClientBuilder()/*.connectionPoolSize(10)*/.register(customJacksonProvider);
			if(NumberHelper.isGreaterThanZero(CONNECTION_POOL_SIZE))
				resteasyClientBuilder.connectionPoolSize(CONNECTION_POOL_SIZE);
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
			customJacksonProvider.setMapper(objectMapper);
			
			ResteasyClient resteasyClient = resteasyClientBuilder.build();
			
			CLIENT.set(KeycloakBuilder.builder().grantType(OAuth2Constants.PASSWORD).resteasyClient(resteasyClient)
					.serverUrl(ValueHelper.returnOrThrowIfBlank("keycloak server url",ConfigurationHelper.getValueAsString(VARIABLE_NAME_KEYCLOAK_SERVER_URL)))
					.realm(ValueHelper.returnOrThrowIfBlank("keycloak realm name",ConfigurationHelper.getValueAsString(VARIABLE_NAME_KEYCLOAK_REALM_NAME)))
					.clientId(ValueHelper.returnOrThrowIfBlank("keycloak client identifier",ConfigurationHelper.getValueAsString(VARIABLE_NAME_KEYCLOAK_CLIENT_IDENTIFIER)))
					.clientSecret(ValueHelper.returnOrThrowIfBlank("keycloak client secret",ConfigurationHelper.getValueAsString(VARIABLE_NAME_KEYCLOAK_CLIENT_SECRET)))
					.username(ValueHelper.returnOrThrowIfBlank("keycloak credentials username",ConfigurationHelper.getValueAsString(VARIABLE_NAME_KEYCLOAK_CREDENTIAL_USERNAME)))
					.password(ValueHelper.returnOrThrowIfBlank("keycloak credentials password",ConfigurationHelper.getValueAsString(VARIABLE_NAME_KEYCLOAK_CREDENTIAL_PASSWORD)))				
					.build()
					);
			Keycloak keycloak = (Keycloak) CLIENT.get();
			LogHelper.logInfo("keycloak client instance is "+keycloak, getClass());
			try {
				LogHelper.logInfo("Finding all realms using keycloak client...", getClass());
				keycloak.realms().findAll();
				LogHelper.logInfo("Realms found using keycloak client", getClass());
			} catch (ProcessingException exception) {
				exception.printStackTrace();
				throw new RuntimeException("Realms cannot be found using keycloak client : "+exception.getMessage(), exception);
			}
			return (Keycloak) CLIENT.get();
		}
	}
	
	/**/
	
	static KeycloakClientGetter getInstance() {
		return Helper.getInstance(KeycloakClientGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	Value CLIENT = new Value();
}