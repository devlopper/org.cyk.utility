package org.cyk.utility.__kernel__.security.keycloak;

import javax.ws.rs.ProcessingException;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

public interface KeycloakClientGetter {

	default Keycloak get() {
		if(CLIENT.isHasBeenSet())
			return (Keycloak) CLIENT.get();
		if(!ConfigurationHelper.is(VariableName.KEYCLOAK_ENABLED)) {
			CLIENT.set(null);
			LogHelper.logInfo("keycloak is not enabled", KeycloakClientGetter.class);
			return null;
		}
		CLIENT.set(KeycloakBuilder.builder().grantType(OAuth2Constants.PASSWORD).resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).register(new CustomJacksonProvider()).build())
				.serverUrl(ValueHelper.returnOrThrowIfBlank("keycloak server url",ConfigurationHelper.getValueAsString(VariableName.KEYCLOAK_SERVER_URL)))
				.realm(ValueHelper.returnOrThrowIfBlank("keycloak realm name",ConfigurationHelper.getValueAsString(VariableName.KEYCLOAK_REALM_NAME)))
				.clientId(ValueHelper.returnOrThrowIfBlank("keycloak client identifier",ConfigurationHelper.getValueAsString(VariableName.KEYCLOAK_CLIENT_IDENTIFIER)))
				.clientSecret(ValueHelper.returnOrThrowIfBlank("keycloak client secret",ConfigurationHelper.getValueAsString(VariableName.KEYCLOAK_CLIENT_SECRET)))
				.username(ValueHelper.returnOrThrowIfBlank("keycloak credentials username",ConfigurationHelper.getValueAsString(VariableName.KEYCLOAK_CREDENTIAL_USERNAME)))
				.password(ValueHelper.returnOrThrowIfBlank("keycloak credentials password",ConfigurationHelper.getValueAsString(VariableName.KEYCLOAK_CREDENTIAL_PASSWORD)))				
				.build()
				);
		Keycloak keycloak = (Keycloak) CLIENT.get();
		try {
			keycloak.realms().findAll();
			LogHelper.logInfo("keycloak client has been created", KeycloakClientGetter.class);
		} catch (ProcessingException exception) {
			throw new RuntimeException("keycloak client cannot be created : "+exception.getMessage(), exception);
		}		
		return (Keycloak) CLIENT.get();
	}
	
	/**/
	
	static KeycloakClientGetter getInstance() {
		return Helper.getInstance(KeycloakClientGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	Value CLIENT = new Value();
}