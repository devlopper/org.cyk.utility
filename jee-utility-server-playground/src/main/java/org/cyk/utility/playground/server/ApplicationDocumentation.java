package org.cyk.utility.playground.server;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.configuration.ConstantParameterName;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.value.ValueHelper;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath(ApplicationDocumentation.PATH)
@Path("/")
@ApplicationScoped
public class ApplicationDocumentation extends Application {

	public ApplicationDocumentation() {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion(ValueHelper.defaultToIfBlank(ConfigurationHelper.getValueAsString(ConstantParameterName.SYSTEM_VERSION),"version not defined"));
		beanConfig.setSchemes(new String[] { "http" });
		String host = ValueHelper.defaultToIfBlank(ConfigurationHelper.getValueAsString(ConstantParameterName.SYSTEM_HOST),"localhost");
		String port = ValueHelper.defaultToIfBlank(ConfigurationHelper.getValueAsString(ConstantParameterName.SYSTEM_PORT),"8080");
		beanConfig.setHost(host+":"+port);
		beanConfig.setBasePath("/"+ConfigurationHelper.getValueAsString(ConstantParameterName.SYSTEM_WEB_CONTEXT, null, null,ConstantEmpty.STRING)+PATH);
		
		beanConfig.setResourcePackage(ValueHelper.defaultToIfBlank(ConfigurationHelper.getValueAsString(ConstantParameterName.SWAGGER_BEAN_CONFIG_RESOURCE_PACKAGE)
				,getClass().getPackage().getName()+".representation.api"));
		
		beanConfig.setTitle(ValueHelper.defaultToIfBlank(ConfigurationHelper.getValueAsString(ConstantParameterName.SYSTEM_NAME),"System name not defined")
				+" : Application Programming Interface Documentation");
		beanConfig.setDescription("Documentation of API using Swagger");
		beanConfig.setScan(true);
	}

	public static final String PATH = "/documentation";
}