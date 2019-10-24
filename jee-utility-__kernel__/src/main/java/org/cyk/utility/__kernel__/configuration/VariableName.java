package org.cyk.utility.__kernel__.configuration;

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
	String SYSTEM_NAME = buildSystem("name");
	String SYSTEM_HOST = buildSystem("host");
	String SYSTEM_PORT = buildSystem("port");
	String SYSTEM_WEB_CONTEXT = buildSystem("web.context");

	/* Protocols */
	
	String PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_IS_ENABLE = buildProtocolSimpleMailTransfer("is.enabled");
	String PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_HOST = buildProtocolSimpleMailTransfer("host");
	String PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_PORT = buildProtocolSimpleMailTransfer("port");
	String PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_IS_AUTHENTICATION_REQUIRED = buildProtocolSimpleMailTransfer("is.authentication.required");
	String PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_IS_SECURED_CONNECTION_REQUIRED = buildProtocolSimpleMailTransfer("is.secured.connection.required");
	String PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_IDENTIFIER = buildProtocolSimpleMailTransfer("authentication.credentials.user.identifier");
	String PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_SECRET = buildProtocolSimpleMailTransfer("authentication.credentials.user.secret");
	
	String PROXY_UNIFORM_RESOURCE_IDENTIFIER = buildSystem("proxy.uniform.resource.identifier");
	
	/* Data */

	String DATA_IS_LOADABLE = buildSystem("data.is.loadable");
	
	/* Security */

	String SECURITY_DELEGATE_SYSTEM_IS_ENABLE = buildSystem("security.delegate.system.is.enable");
	
	/* Stream Distributed*/
	
	String STREAM_DISTRIBUTED_DELEGATE_SYSTEM_IS_ENABLE = buildSystem("stream.distributed.delegate.system.is.enable");
	
	/* Swagger */
	
	String SWAGGER_ENABLED = buildSwagger("enabled");
	String SWAGGER_BEAN_CONFIG_RESOURCE_PACKAGE = buildSwaggerBeanConfig("resource.package");
	
	/* User Interface */
	
	/* 		Theme */
	
	String USER_INTERFACE_THEME_CLASS_NAME = buildUserInterfaceTheme("class.name");
	String USER_INTERFACE_THEME_IDENTIFIER = buildUserInterfaceTheme("identifier");
	String USER_INTERFACE_THEME_PRIMEFACES = buildUserInterfaceTheme("primefaces");
	
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
	
	/**/
	
	String NAME_FORMAT = "cyk.variable.%s";
	
	static String build(Collection<Object> objects) {
		if(CollectionHelper.isEmpty(objects))
			return null;
		String value = StringHelper.concatenateFromObjects(objects, ConstantCharacter.VERTICAL_BAR);
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
		return build(klass,FieldHelper.join(paths));
	}
	
	static String buildFieldName(Class<?> klass,Collection<String> paths) {
		return buildFieldName(klass, null,paths);
	}
	
	static String buildFieldName(Class<?> klass,Object classifier,String...paths) {
		return buildFieldName(klass, CollectionHelper.listOf(paths));
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
	
	
}
