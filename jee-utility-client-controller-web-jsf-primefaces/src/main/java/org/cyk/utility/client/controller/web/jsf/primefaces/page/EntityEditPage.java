package org.cyk.utility.client.controller.web.jsf.primefaces.page;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionList;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.window.WindowBuilder;
import org.cyk.utility.client.controller.navigation.Navigation;
import org.cyk.utility.client.controller.navigation.NavigationBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class EntityEditPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __processWindowDialogOkCommandableGetUrl__(WindowBuilder window,CommandableBuilder commandable) {
		SystemAction systemAction = window.getContainerManaged().getSystemAction();
		NavigationBuilder navigationBuilder = __inject__(NavigationBuilder.class)
				.setIdentifierBuilderSystemAction(__inject__(SystemActionList.class).setEntityClass(systemAction.getEntityClass()));
		navigationBuilder.getProperties().setUniformResourceLocatorMap(__getUniformResourceLocatorMap__());
		navigationBuilder.getProperties().setContext(__getContext__());
		Navigation navigation = navigationBuilder.execute().getOutput();
		return navigation.getUniformResourceLocator().toString() ;
	}
	
}
