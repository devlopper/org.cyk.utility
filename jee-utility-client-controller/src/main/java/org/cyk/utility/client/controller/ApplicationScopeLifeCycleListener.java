package org.cyk.utility.client.controller;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.annotation.Controller;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.component.window.WindowRenderTypeDialog;
import org.cyk.utility.client.controller.component.window.WindowRenderTypeNormal;
import org.cyk.utility.client.controller.icon.IconIdentifierGetterFontAwsome;
import org.cyk.utility.client.controller.icon.IconIdentifierGetterImpl;
import org.cyk.utility.instance.InstanceGetter;
import org.cyk.utility.system.node.SystemNodeClient;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		__setQualifierClassTo__(Controller.class, InstanceGetter.class);
		//TODO remove FunctionRunnableImpl
		__inject__(FunctionRunnableMap.class).set(IconIdentifierGetterImpl.class, IconIdentifierGetterFontAwsome.class,LEVEL);
		//__inject__(FunctionRunnableMap.class).set(ProxyGetterImpl.class, ProxyGetterRestEasyFunctionRunnableImpl.class,LEVEL);
		
		__initializeApplication__(object);
		
		__inject__(org.cyk.utility.ApplicationScopeLifeCycleListener.class).initialize(null);
		ParameterName.addClasses(WindowRenderTypeNormal.class,WindowRenderTypeDialog.class);
	}
	
	protected void __initializeApplication__(Object object) {
		SystemNodeClient systemClient = __inject__(SystemNodeClient.class);
		if(StringHelper.isBlank(systemClient.getName()))
			systemClient.setName("CLIENT_APP_NAME");
	}
	
	@Override
	public void __destroy__(Object object) {}
	
	/**/
	
	@Deprecated
	public static final Integer LEVEL = Integer.valueOf(org.cyk.utility.ApplicationScopeLifeCycleListener.LEVEL+1);
	
}
