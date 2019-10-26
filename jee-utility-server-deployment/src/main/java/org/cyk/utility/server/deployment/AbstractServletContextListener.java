package org.cyk.utility.server.deployment;
import java.io.Serializable;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.lang3.RegExUtils;
import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.context.ContextHelper;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.cyk.utility.network.protocol.ProtocolDefaults;
import org.cyk.utility.security.Credentials;
import org.cyk.utility.server.representation.impl.ApplicationProgrammingInterface;
import org.cyk.utility.server.representation.impl.ApplicationScopeLifeCycleListener;

import io.swagger.jaxrs.config.BeanConfig;

public abstract class AbstractServletContextListener extends org.cyk.utility.context.AbstractSystemContextListenerImpl<ServletContext> implements javax.servlet.ServletContextListener,Objectable,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext context = servletContextEvent.getServletContext();
		initialize(context);	
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Override
	public void __initialize__(ServletContext context) {
		ContextHelper.set(context);
		super.__initialize__(context);
		if(ConfigurationHelper.is(VariableName.PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_IS_ENABLE)) {
			__inject__(ProtocolDefaults.class)
			.getSimpleMailTransfer()
				.setHost(ConfigurationHelper.getValueAsString(VariableName.PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_HOST))
				.setPort(ConfigurationHelper.getValueAsInteger(VariableName.PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_PORT))
				.setIsAuthenticationRequired(ConfigurationHelper.is(VariableName.PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_IS_AUTHENTICATION_REQUIRED))
				.setIsSecuredConnectionRequired(ConfigurationHelper.is(VariableName.PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_IS_SECURED_CONNECTION_REQUIRED))
				.setAuthenticationCredentials(__inject__(Credentials.class)
						.setIdentifier(ConfigurationHelper.getValueAsString(VariableName.PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_IDENTIFIER))
						.setSecret(ConfigurationHelper.getValueAsString(VariableName.PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_SECRET)));	
		}
		
		if(ConfigurationHelper.is(VariableName.DATA_IS_LOADABLE))
			__saveData__();	
		
		if(ConfigurationHelper.is(VariableName.SWAGGER_ENABLED)) {
			BeanConfig beanConfig = new BeanConfig();
			beanConfig.setVersion(ValueHelper.defaultToIfBlank(ConfigurationHelper.getValueAsString(VariableName.SYSTEM_VERSION),"version not defined"));
			beanConfig.setSchemes(new String[] { "http" });
			String host = ValueHelper.defaultToIfBlank(ConfigurationHelper.getValueAsString(VariableName.SYSTEM_HOST),"localhost");
			String port = ValueHelper.defaultToIfBlank(ConfigurationHelper.getValueAsString(VariableName.SYSTEM_PORT),"8080");
			beanConfig.setHost(host+":"+port);
			String contextPath = "/"+ValueHelper.defaultToIfBlank(ConfigurationHelper.getValueAsString(VariableName.SYSTEM_WEB_CONTEXT),ConstantEmpty.STRING)+ApplicationProgrammingInterface.PATH;
			contextPath = RegExUtils.replaceAll(contextPath, "////", "/");
			contextPath = RegExUtils.replaceAll(contextPath, "///", "/");
			contextPath = RegExUtils.replaceAll(contextPath, "//", "/");				
			beanConfig.setBasePath(contextPath);			
			beanConfig.setResourcePackage(ConfigurationHelper.getValueAsString(VariableName.SWAGGER_BEAN_CONFIG_RESOURCE_PACKAGE));			
			beanConfig.setTitle(ValueHelper.defaultToIfBlank(ConfigurationHelper.getValueAsString(VariableName.SYSTEM_NAME),"System name not defined")
					+" : Application Programming Interface Documentation");
			beanConfig.setDescription("Documentation of API using Swagger");
			beanConfig.setScan(true);	
		}		
	}
	
	protected void __saveData__() {
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ServletContext context = servletContextEvent.getServletContext();
		destroy(context);	
		//__inject__(ApplicationScopeLifeCycleListener.class).destroy(null);
	}
	

}
