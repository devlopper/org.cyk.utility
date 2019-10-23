package org.cyk.utility.__kernel__.configuration;

public interface VariableName {

	/* System */
	
	String SYSTEM_IDENTIFIER = buildSystem("identifier");
	String SYSTEM_VERSION = buildSystem("version");
	String SYSTEM_NAME = buildSystem("name");
	String SYSTEM_HOST = buildSystem("host");
	String SYSTEM_PORT = buildSystem("port");
	String SYSTEM_WEB_CONTEXT = buildSystem("web.context");

	/* Swagger */
	
	String SWAGGER_ENABLED = buildSwagger("enabled");
	String SWAGGER_BEAN_CONFIG_RESOURCE_PACKAGE = buildSwaggerBeanConfig("resource.package");
	
	/**/
	
	String NAME_FORMAT = "cyk.variable.%s";
	
	static String build(String string) {
		return String.format(NAME_FORMAT,string);
	}
	
	static String buildSystem(String string) {
		return build("system."+string);
	}
	
	static String buildSwagger(String string) {
		return build("swagger."+string);
	}
	
	static String buildSwaggerBeanConfig(String string) {
		return buildSwagger("bean.config."+string);
	}
}
