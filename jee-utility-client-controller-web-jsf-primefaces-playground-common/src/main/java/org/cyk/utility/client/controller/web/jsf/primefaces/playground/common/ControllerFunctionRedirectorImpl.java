package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import org.cyk.utility.__kernel__.annotation.Default;
import org.cyk.utility.client.controller.AbstractControllerFunctionRedirectorImpl;
import org.cyk.utility.client.controller.navigation.Navigation;
import org.cyk.utility.client.controller.navigation.NavigationBuilder;
import org.cyk.utility.client.controller.navigation.NavigationRedirector;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionRead;

@Default
public class ControllerFunctionRedirectorImpl extends AbstractControllerFunctionRedirectorImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(SystemAction action) {
		SystemAction targetSystemAction = getTargetSystemAction();
		if(targetSystemAction == null) {
			targetSystemAction = __inject__(SystemActionRead.class);
		}
		
		targetSystemAction.setEntityClass(getEntityClass());
		targetSystemAction.getEntitiesIdentifiers(Boolean.TRUE).add(action.getEntitiesIdentifiers().getFirst());
		
		NavigationBuilder navigationBuilder = __inject__(NavigationBuilder.class).setIdentifierBuilderSystemAction(targetSystemAction);
		Navigation navigation = navigationBuilder.execute().getOutput();
		__inject__(NavigationRedirector.class).setNavigation(navigation).execute();	
	}

}
