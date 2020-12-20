package org.cyk.utility.__kernel__.variable;

import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface VariableName {

	/* System */
	
	String SYSTEM_IDENTIFIER = buildSystem("identifier");
	String SYSTEM_VERSION = buildSystem("version");
	String SYSTEM_TIMESTAMP = buildSystem("timestamp");
	String SYSTEM_TIMESTAMP_AS_STRING = buildSystem("timestamp.as.string");
	String SYSTEM_NAME = buildSystem("name");
	
	String SYSTEM_UNIFORM_RESOURCE_IDENTIFIER_SCHEME = buildSystem("uri.scheme");
	String SYSTEM_UNIFORM_RESOURCE_IDENTIFIER_HOST = buildSystem("uri.host");
	String SYSTEM_UNIFORM_RESOURCE_IDENTIFIER_PORT = buildSystem("uri.port");
	String SYSTEM_UNIFORM_RESOURCE_IDENTIFIER_CONTEXT = buildSystem("uri.context");
	
	String SYSTEM_WEB_CONTEXT = buildSystem("web.context");
	String SYSTEM_WEB_HOME_URL = buildSystem("web.home.url");
	String SYSTEM_LOGGING_ENABLED = buildSystem("logging.enabled");
	String SYSTEM_LOGGING_THROWABLE_PRINT_STACK_TRACE = buildSystem("logging.throwable.print.stack.trace");
	
	/* Protocols */
	
	String PROTOCOL_SIMPLE_MAIL_TRANSFER_ENABLE = buildProtocolSimpleMailTransfer("enabled");
	String PROTOCOL_SIMPLE_MAIL_TRANSFER_HOST = buildProtocolSimpleMailTransfer("host");
	String PROTOCOL_SIMPLE_MAIL_TRANSFER_PORT = buildProtocolSimpleMailTransfer("port");
	String PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_REQUIRED = buildProtocolSimpleMailTransfer("authentication.required");
	String PROTOCOL_SIMPLE_MAIL_TRANSFER_SECURED_CONNECTION_REQUIRED = buildProtocolSimpleMailTransfer("secured.connection.required");
	String PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_IDENTIFIER = buildProtocolSimpleMailTransfer("authentication.credentials.user.identifier");
	String PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_SECRET = buildProtocolSimpleMailTransfer("authentication.credentials.user.secret");
	String PROTOCOL_SIMPLE_MAIL_TRANSFER_PROXY_HOST = buildProtocolSimpleMailTransfer("proxy.host");
	String PROTOCOL_SIMPLE_MAIL_TRANSFER_PROXY_PORT = buildProtocolSimpleMailTransfer("proxy.port");
	String PROTOCOL_SIMPLE_MAIL_TRANSFER_PROXY_AUTHENTICATION_CREDENTIALS_USER_IDENTIFIER = buildProtocolSimpleMailTransfer("proxy.authentication.credentials.user.identifier");
	String PROTOCOL_SIMPLE_MAIL_TRANSFER_PROXY_AUTHENTICATION_CREDENTIALS_USER_SECRET = buildProtocolSimpleMailTransfer("proxy.authentication.credentials.user.secret");
	
	String PROXY_UNIFORM_RESOURCE_IDENTIFIER = buildSystem("proxy.uniform.resource.identifier");
	
	/* Data */

	String DATA_IS_LOADABLE = buildSystem("data.is.loadable");
	
	/* Security */

	String SECURITY_AUTHENTICATION_ENABLE = buildSystem("security.authentication.enable");
	String SECURITY_DELEGATE_SYSTEM_IS_ENABLE = buildSystem("security.delegate.system.is.enable");
	
	/* Keycloak */
	
	String KEYCLOAK_ENABLED = buildKeycloak("enabled");
	String KEYCLOAK_SERVER_URL = buildKeycloak("server.url");
	String KEYCLOAK_REALM_NAME = buildKeycloak("realm.name");
	String KEYCLOAK_CLIENT_IDENTIFIER = buildKeycloak("client.identifier");
	String KEYCLOAK_CLIENT_SECRET = buildKeycloak("client.secret");
	String KEYCLOAK_CREDENTIAL_USERNAME = buildKeycloak("credential.username");
	String KEYCLOAK_CREDENTIAL_PASSWORD = buildKeycloak("credential.password");
	
	/* Jasper */
	static String buildJasper(String string) {
		return build("jasper."+string);
	}
	String JASPER_ENABLED = buildJasper("enabled");
	String JASPER_SERVER_URL = buildJasper("server.url");
	String JASPER_SERVER_CREDENTIAL_USERNAME = buildJasper("server.credential.username");
	String JASPER_SERVER_CREDENTIAL_PASSWORD = buildJasper("server.credential.password");
	
	/* Stream Distributed*/
	
	String STREAM_DISTRIBUTED_DELEGATE_SYSTEM_IS_ENABLE = buildSystem("stream.distributed.delegate.system.is.enable");
	
	/* Swagger */
	
	String SWAGGER_ENABLED = buildSwagger("enabled");
	String SWAGGER_BEAN_CONFIG_RESOURCE_PACKAGE = buildSwaggerBeanConfig("resource.package");
	
	/* User Interface */
	
	/* 		Theme */
	
	String USER_INTERFACE_THEME_CLASS_NAME = buildUserInterfaceTheme("class.name");
	String USER_INTERFACE_THEME_IDENTIFIER = buildUserInterfaceTheme("identifier");
	String USER_INTERFACE_THEME_COLOR = buildUserInterfaceTheme("color");
	String USER_INTERFACE_THEME_PRIMEFACES = buildUserInterfaceTheme("primefaces");
	String USER_INTERFACE_THEME_JSF_CONTRACT = buildUserInterfaceTheme("jsf.contract");
	
	/*		Favicon*/
	
	String USER_INTERFACE_THEME_FAVICON_FILE_RESOURCES_FOLDER = buildUserInterfaceTheme("favicon.file.resources.folder");
	String USER_INTERFACE_THEME_FAVICON_FILE_FOLDER = buildUserInterfaceTheme("favicon.file.folder");
	String USER_INTERFACE_THEME_FAVICON_FILE_NAME_PREFIX = buildUserInterfaceTheme("favicon.file.name.prefix");
	String USER_INTERFACE_THEME_FAVICON_FILE_NAME_EXTENSION = buildUserInterfaceTheme("favicon.file.name.extension");
	
	/*		Logo*/
	
	String USER_INTERFACE_THEME_LOGO_FILE_RESOURCES_FOLDER = buildUserInterfaceTheme("logo.file.resources.folder");
	String USER_INTERFACE_THEME_LOGO_FILE_FOLDER = buildUserInterfaceTheme("logo.file.folder");
	String USER_INTERFACE_THEME_LOGO_FILE_NAME = buildUserInterfaceTheme("logo.file.name");
	String USER_INTERFACE_THEME_LOGO_FILE_NAME_PREFIX = buildUserInterfaceTheme("logo.file.name.prefix");
	String USER_INTERFACE_THEME_LOGO_FILE_NAME_EXTENSION = buildUserInterfaceTheme("logo.file.name.extension");
	
	/* 		Menu*/
	
	String USER_INTERFACE_THEME_MENU_PATH = buildUserInterfaceTheme("menu.path");
	String USER_INTERFACE_THEME_MENU_IDENTIFIER = buildUserInterfaceTheme("menu.identifier");
	String USER_INTERFACE_THEME_MENU_IS_DYNAMIC = buildUserInterfaceTheme("menu.is.dynamic");
	
	/**/
	
	String NAME_FORMAT = "cyk.variable.%s";
	
	static String build(Collection<Object> objects) {
		if(CollectionHelper.isEmpty(objects))
			return null;
		String value = StringHelper.concatenateFromObjects(objects, SEPARATOR);
		if(StringHelper.isBlank(value))
			return null;
		return String.format(NAME_FORMAT,value);
	}
	
	static String build(Object...objects) {
		if(ArrayHelper.isEmpty(objects))
			return null;
		return build(CollectionHelper.listOf(Boolean.TRUE,objects));
	}
	
	/* System */
	
	static String buildSystem(String string) {
		return build("system."+string);
	}
	
	/* Protocol */
	
	static String buildProtocol(String string) {
		return build("protocol."+string);
	}
	
	static String buildProtocolSimpleMailTransfer(String string) {
		return buildProtocol("simple.mail.transfer."+string);
	}
	
	/* Class */
	
	static String buildClassUniformResourceIdentifier(Class<?> klass,Object classifier) {
		return build(klass,classifier,"uri");
	}
	
	static String buildClassUniformResourceIdentifier(Class<?> klass) {
		return buildClassUniformResourceIdentifier(klass,null);
	}
	
	/* Field */
	
	static String buildFieldName(Class<?> klass,Object classifier,Collection<String> paths) {
		return build(klass,classifier,FieldHelper.join(paths));
	}
	
	static String buildFieldName(Class<?> klass,Collection<String> paths) {
		return buildFieldName(klass, null,paths);
	}
	
	static String buildFieldName(Class<?> klass,Object classifier,String...paths) {
		return buildFieldName(klass, classifier,CollectionHelper.listOf(paths));
	}
	
	static String buildFieldName(Class<?> klass,String...paths) {
		return buildFieldName(klass,null,paths);
	}
	
	/* Swagger */
	
	static String buildSwagger(String string) {
		return build("swagger."+string);
	}
	
	static String buildSwaggerBeanConfig(String string) {
		return buildSwagger("bean.config."+string);
	}
	
	/* User Interface */
	
	static String buildUserInterface(String string) {
		return build("user.interface."+string);
	}
	
	static String buildUserInterfaceTheme(String string) {
		return buildUserInterface("theme."+string);
	}
	
	/* Keycloak */
	
	static String buildKeycloak(String string) {
		return build("keycloak."+string);
	}
	
	/**/
	
	static String SEPARATOR = ConstantCharacter.HYPHEN.toString();
}
