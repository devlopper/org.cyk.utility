package org.cyk.utility.client.controller;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.annotation.Controller;
import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.instance.InstanceGetter;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.action.SystemActionFieldsGetter;
import org.cyk.utility.__kernel__.system.action.SystemActionFieldsNamesGetter;
import org.cyk.utility.client.controller.component.window.WindowRenderType;
import org.cyk.utility.client.controller.component.window.WindowRenderTypeDialog;
import org.cyk.utility.client.controller.component.window.WindowRenderTypeNormal;
import org.cyk.utility.system.node.SystemNodeClient;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		initialize();
	}
	
	public static void initialize() {
		initialize(ApplicationScopeLifeCycleListener.class, new Runnable() {
			@Override
			public void run() {
				__setQualifierClassTo__(Controller.class, InstanceGetter.class,SystemActionFieldsGetter.class,SystemActionFieldsNamesGetter.class);
				//__initializeApplication__(object);		
				org.cyk.utility.ApplicationScopeLifeCycleListener.initialize();
				ParameterName.WINDOW_RENDER_TYPE_CLASS.setType(WindowRenderType.class);
				ParameterName.addClasses(WindowRenderTypeNormal.class,WindowRenderTypeDialog.class);
			}
		});
	}
	
	@Deprecated
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
