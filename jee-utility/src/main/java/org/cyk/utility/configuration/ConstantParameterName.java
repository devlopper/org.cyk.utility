package org.cyk.utility.configuration;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.annotation.Configuration;
import org.cyk.utility.type.BooleanHelper;
import org.cyk.utility.value.IsNullChecker;

public interface ConstantParameterName {
	
	String CYK_PARAMETER_NAME_FORMAT = "cyk.parameter.%s";
	static String formatCykParameterName(String string) {
		return String.format(CYK_PARAMETER_NAME_FORMAT,string);
	}
	static String formatCykParameterNameProtocolDefaultsSimpleMailTransfer(String string) {
		return formatCykParameterName("protocol.defaults.simple.mail.transfer."+string);
	}
	
	/* System */
	
	String SYSTEM_NAME = formatCykParameterName("system.name");
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
	
	/**/
	
	static String get(String name,Object context,Object request,String nullValue) {
		return (String) DependencyInjection.inject(ConfigurationParameterValueGetter.class).setName(name).setContext(context).setRequest(request)
				.setIsNullValueCheckerClass(IsNullChecker.class).setIsNullValueCheckerQualifierClass(Configuration.class).setNullValue(nullValue).execute().getOutput();
	}
	
	/**/
	
	static String get(String name,Object context,Object request) {
		return get(name, context, request, null);
	}
	
	static String get(String name,Object context) {
		return get(name,context, null);
	}
	
	static String get(String name) {
		return get(name,null);
	}
	
	static Boolean is(String name,Object context,Object request) {
		return DependencyInjection.inject(BooleanHelper.class).get(get(name, context, request, Boolean.FALSE.toString()));
	}
	
	static Boolean is(String name,Object context) {
		return is(name,context, null);
	}
	
	static Boolean is(String name) {
		return is(name,null);
	}
}
