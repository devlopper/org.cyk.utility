package org.cyk.utility.server.deployment;
import java.io.Serializable;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.context.ContextHelper;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.cyk.utility.network.protocol.ProtocolDefaults;
import org.cyk.utility.security.Credentials;
import org.cyk.utility.server.representation.impl.ApplicationScopeLifeCycleListener;


public abstract class AbstractServletContextListener extends org.cyk.utility.context.AbstractSystemContextListenerImpl<ServletContext> implements javax.servlet.ServletContextListener,Objectable,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext context = servletContextEvent.getServletContext();
		initialize(context);	
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
		//if(Boolean.TRUE.equals(ConfigurationHelper.getValueAsBoolean(VariableName.SECURITY_DELEGATE_SYSTEM_IS_ENABLE)))
		//	;
	}
	
	@Override
	public void __initialize__(ServletContext context) {
		ContextHelper.set(context);
		super.__initialize__(context);
		/*
		VariableHelper.write(VariableName.SYSTEM_UNIFORM_RESOURCE_IDENTIFIER_SCHEME, "http");
		VariableHelper.write(VariableName.SYSTEM_UNIFORM_RESOURCE_IDENTIFIER_PORT, "80");
		VariableHelper.write(VariableName.SYSTEM_UNIFORM_RESOURCE_IDENTIFIER_CONTEXT, "/api");
		*/
		if(ConfigurationHelper.is(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_ENABLE)) {
			__inject__(ProtocolDefaults.class)
			.getSimpleMailTransfer()
				.setHost(ConfigurationHelper.getValueAsString(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_HOST))
				.setPort(ConfigurationHelper.getValueAsInteger(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_PORT))
				.setIsAuthenticationRequired(ConfigurationHelper.is(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_REQUIRED))
				.setIsSecuredConnectionRequired(ConfigurationHelper.is(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_SECURED_CONNECTION_REQUIRED))
				.setAuthenticationCredentials(__inject__(Credentials.class)
						.setIdentifier(ConfigurationHelper.getValueAsString(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_IDENTIFIER))
						.setSecret(ConfigurationHelper.getValueAsString(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_SECRET)));	
		}
		
		if(ConfigurationHelper.is(VariableName.DATA_IS_LOADABLE))
			__saveData__();	
		
		/*if(ConfigurationHelper.is(VariableName.SWAGGER_ENABLED)) {
			BeanConfig beanConfig = new BeanConfig();
			beanConfig.setVersion(ValueHelper.defaultToIfBlank(ConfigurationHelper.getValueAsString(VariableName.SYSTEM_VERSION),"version not defined"));
			String contextPath = "/"+ValueHelper.defaultToIfBlank(ConfigurationHelper.getValueAsString(VariableName.SYSTEM_WEB_CONTEXT),ConstantEmpty.STRING)+ApplicationProgrammingInterface.PATH;
			contextPath = RegExUtils.replaceAll(contextPath, "////", "/");
			contextPath = RegExUtils.replaceAll(contextPath, "///", "/");
			contextPath = RegExUtils.replaceAll(contextPath, "//", "/");				
			beanConfig.setBasePath(contextPath);			
			beanConfig.setResourcePackage(ConfigurationHelper.getValueAsString(VariableName.SWAGGER_BEAN_CONFIG_RESOURCE_PACKAGE));			
			beanConfig.setTitle(ValueHelper.defaultToIfBlank(ConfigurationHelper.getValueAsString(VariableName.SYSTEM_NAME),"System name not defined")
					+" : API Documentation");
			String description = "Documentation of API using Swagger";
			String timestampAsString = ConfigurationHelper.getValueAsString(VariableName.SYSTEM_TIMESTAMP_AS_STRING);
			if(StringHelper.isBlank(timestampAsString)) {
				Long timestamp = ConfigurationHelper.getValueAsLong(VariableName.SYSTEM_TIMESTAMP);
				if(timestamp != null)
					timestampAsString = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),ZoneId.systemDefault()).toString();
			}
			if(StringHelper.isNotBlank(timestampAsString))
				description = description + " - Built on "+timestampAsString;
			beanConfig.setDescription(description);
			beanConfig.setContact("komenanyc@yahoo.fr.com");
			beanConfig.setExpandSuperTypes(Boolean.FALSE);
			beanConfig.setScan(true);
		}*/
	}
	
	protected void __saveData__() {
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ServletContext context = servletContextEvent.getServletContext();
		destroy(context);	
		//__inject__(ApplicationScopeLifeCycleListener.class).destroy(null);
	}
	
	/**/
	
	
}