package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.component.command.CommandFunctionExecuteListenerThroughImpl;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMapGetterImpl;
import org.cyk.utility.client.controller.entities.MyEntity;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierParameterValueMatrix;
import org.cyk.utility.instance.InstanceGetterImpl;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __initialize__(Object object) {
		__inject__(FunctionRunnableMap.class).set(CommandFunctionExecuteListenerThroughImpl.class, CommandFunctionExecuteListenerThroughFunctionRunnableImpl.class,Boolean.TRUE);
		__inject__(FunctionRunnableMap.class).set(MenuBuilderMapGetterImpl.class, MenuBuilderMapGetterFunctionRunnableImpl.class,Boolean.TRUE);
		__inject__(FunctionRunnableMap.class).set(InstanceGetterImpl.class, InstanceGetterFunctionRunnableImpl.class,Boolean.TRUE);
		
		__inject__(UniformResourceIdentifierParameterValueMatrix.class).setClass(MyEntity.class);
	}
	
	@Override
	protected void __destroy__(Object object) {}
	
}
