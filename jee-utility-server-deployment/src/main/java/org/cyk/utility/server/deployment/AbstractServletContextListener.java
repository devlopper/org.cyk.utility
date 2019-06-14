package org.cyk.utility.server.deployment;
import java.io.Serializable;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.annotation.Web;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.configuration.ConstantParameterName;
import org.cyk.utility.context.ContextGetter;
import org.cyk.utility.context.ContextParameterValueGetter;
import org.cyk.utility.network.protocol.ProtocolDefaults;
import org.cyk.utility.security.Credentials;
import org.cyk.utility.server.representation.impl.ApplicationScopeLifeCycleListener;
import org.cyk.utility.system.node.SystemNodeServer;

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
		AbstractContextGetterImpl.SERVLET_CONTEXT = context;
		super.__initialize__(context);
		DependencyInjection.setQualifierClassTo(Web.class, ContextGetter.class,ContextParameterValueGetter.class);
		__inject__(SystemNodeServer.class).setName(getConfigurationParameterValue(ConstantParameterName.SYSTEM_NAME));
		
		if(Boolean.TRUE.equals(getConfigurationParameterValueAsBoolean(ConstantParameterName.PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_IS_ENABLE))) {
			__inject__(ProtocolDefaults.class)
			.getSimpleMailTransfer()
				.setHost(getConfigurationParameterValue(ConstantParameterName.PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_HOST))
				.setPort(getConfigurationParameterValueAsInteger(ConstantParameterName.PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_PORT))
				.setIsAuthenticationRequired(getConfigurationParameterValueAsBoolean(ConstantParameterName.PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_IS_AUTHENTICATION_REQUIRED))
				.setIsSecuredConnectionRequired(getConfigurationParameterValueAsBoolean(ConstantParameterName.PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_IS_SECURED_CONNECTION_REQUIRED))
				.setAuthenticationCredentials(__inject__(Credentials.class)
						.setIdentifier(getConfigurationParameterValue(ConstantParameterName.PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_IDENTIFIER))
						.setSecret(getConfigurationParameterValue(ConstantParameterName.PROTOCOL_DEFAULTS_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_SECRET)));	
		}
		
		if(Boolean.TRUE.equals(getConfigurationParameterValueAsBoolean(ConstantParameterName.DATA_IS_LOADABLE)))
			__saveData__();	
		
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
