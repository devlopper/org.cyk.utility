package org.cyk.utility.__kernel__.configuration;

import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.field.FieldHelper;

//TODO to be rename to VariableName and as enumeration
public interface ConstantParameterName {
	
	String CYK_PARAMETER_NAME_FORMAT = "cyk.parameter.%s";
	static String formatCykParameterName(String string) {
		return String.format(CYK_PARAMETER_NAME_FORMAT,string);
	}
	
	static String formatCykParameterNameProtocolDefaultsSimpleMailTransfer(String string) {
		return formatCykParameterName("protocol.defaults.simple.mail.transfer."+string);
	}
	
	static String formatCykParameterNameClassUniformResourceIdentifier(Class<?> klass) {
		return formatCykParameterName(klass.getName()+ConstantCharacter.UNDESCORE+"uri");
	}
	
	static String formatCykParameterNameFieldName(Class<?> klass,Collection<String> paths) {
		return formatCykParameterName(klass.getName()+FieldHelper.DOT+FieldHelper.join(paths));
	}
	
	static String formatCykParameterNameFieldName(Class<?> klass,String...paths) {
		return formatCykParameterNameFieldName(klass, CollectionHelper.listOf(paths));
	}
	
	/* System */
	
	String SYSTEM_NAME = formatCykParameterName("system.name");
	String SYSTEM_VERSION = formatCykParameterName("system.version");
	String SYSTEM_HOST = formatCykParameterName("system.host");
	String SYSTEM_PORT = formatCykParameterName("system.port");
	String SYSTEM_WEB_CONTEXT = formatCykParameterName("system.web.context");
	String SYSTEM_NODE_CLIENT_NAME = formatCykParameterName("system.node.client.name");
	String SYSTEM_NODE_SERVER_NAME = formatCykParameterName("system.node.server.name");
	
	String PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_IS_ENABLE = formatCykParameterNameProtocolDefaultsSimpleMailTransfer("is.enable");
	String PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_HOST = formatCykParameterNameProtocolDefaultsSimpleMailTransfer("host");
	String PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_PORT = formatCykParameterNameProtocolDefaultsSimpleMailTransfer("port");
	String PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_IS_AUTHENTICATION_REQUIRED = formatCykParameterNameProtocolDefaultsSimpleMailTransfer("is.authentication.required");
	String PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_IS_SECURED_CONNECTION_REQUIRED = formatCykParameterNameProtocolDefaultsSimpleMailTransfer("is.secured.connection.required");
	String PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_IDENTIFIER = formatCykParameterNameProtocolDefaultsSimpleMailTransfer("authentication.credentials.user.identifier");
	String PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_SECRET = formatCykParameterNameProtocolDefaultsSimpleMailTransfer("authentication.credentials.user.secret");
	
	String PROXY_UNIFORM_RESOURCE_IDENTIFIER = formatCykParameterName("proxy.uniform.resource.identifier");
	
	/* Data */

	String DATA_IS_LOADABLE = formatCykParameterName("data.is.loadable");
	
	/* Security */

	String SECURITY_DELEGATE_SYSTEM_IS_ENABLE = formatCykParameterName("security.delegate.system.is.enable");
	
	/* Stream Distributed*/
	
	String STREAM_DISTRIBUTED_DELEGATE_SYSTEM_IS_ENABLE = formatCykParameterName("stream.distributed.delegate.system.is.enable");
	
	/* Swagger */
	
	String SWAGGER_BEAN_CONFIG_RESOURCE_PACKAGE = formatCykParameterName("swagger.bean.config.resource.package");
}
