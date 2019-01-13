package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMapGetterImpl;
import org.cyk.utility.client.controller.entities.myentity.MyEntity;
import org.cyk.utility.client.controller.entities.verycomplexentity.VeryComplexEntity;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntity;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntityDetails;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierParameterValueMatrix;
import org.cyk.utility.string.repository.StringRepositoryResourceBundle;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		//__inject__(FunctionRunnableMap.class).set(CommandFunctionExecuteListenerThroughImpl.class, CommandFunctionExecuteListenerThroughFunctionRunnableImpl.class,Boolean.TRUE);
		__inject__(FunctionRunnableMap.class).set(MenuBuilderMapGetterImpl.class, MenuBuilderMapGetterFunctionRunnableImpl.class,Boolean.TRUE);
		//__inject__(FunctionRunnableMap.class).set(InstanceGetterImpl.class, InstanceGetterFunctionRunnableImpl.class,Boolean.TRUE);
		
		__inject__(UniformResourceIdentifierParameterValueMatrix.class).setClass(MyEntity.class);
		__inject__(UniformResourceIdentifierParameterValueMatrix.class).setClass(VerySimpleEntity.class);
		__inject__(UniformResourceIdentifierParameterValueMatrix.class).setClass(VerySimpleEntityDetails.class);
		__inject__(UniformResourceIdentifierParameterValueMatrix.class).setClass(VeryComplexEntity.class);
		
		__inject__(StringRepositoryResourceBundle.class).addBundle("org.cyk.utility.client.controller.message");
	}
	
	@Override
	public void __destroy__(Object object) {}
	
}
