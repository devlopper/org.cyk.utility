package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.annotation.Default;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.AbstractApplicationScopeLifeCycleListenerEntities;
import org.cyk.utility.client.controller.ControllerFunctionRedirector;
import org.cyk.utility.client.controller.command.CommandFunction;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMapGetterImpl;
import org.cyk.utility.client.controller.entities.entitynoform.EntityNoForm;
import org.cyk.utility.client.controller.entities.myentity.MyEntity;
import org.cyk.utility.client.controller.entities.verycomplexentity.VeryComplexEntity;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntity;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntityDetails;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierParameterValueMatrix;
import org.cyk.utility.string.repository.StringRepositoryResourceBundle;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListenerEntities implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		super.__initialize__(object);
		__inject__(FunctionRunnableMap.class).set(MenuBuilderMapGetterImpl.class, MenuBuilderMapGetterFunctionRunnableImpl.class,LEVEL);
		
		__setQualifiersClasses__(CommandFunction.class, Default.class);
		__setQualifiersClasses__(ControllerFunctionRedirector.class, Default.class);
		
		__inject__(UniformResourceIdentifierParameterValueMatrix.class).setClass(EntityNoForm.class);
		__inject__(UniformResourceIdentifierParameterValueMatrix.class).setClass(MyEntity.class);
		__inject__(UniformResourceIdentifierParameterValueMatrix.class).setClass(VerySimpleEntity.class);
		__inject__(UniformResourceIdentifierParameterValueMatrix.class).setClass(VerySimpleEntityDetails.class);
		__inject__(UniformResourceIdentifierParameterValueMatrix.class).setClass(VeryComplexEntity.class);
		
		__inject__(StringRepositoryResourceBundle.class).addBundle("org.cyk.utility.client.controller.message");
		
		//__inject__(SystemNodeClient.class).setName("Utility");
	}
	
	@Override
	public void __destroy__(Object object) {}
	
	/**/
	
	public static final Integer LEVEL = new Integer(org.cyk.utility.client.controller.web.jsf.primefaces.ApplicationScopeLifeCycleListener.LEVEL+1);
}
