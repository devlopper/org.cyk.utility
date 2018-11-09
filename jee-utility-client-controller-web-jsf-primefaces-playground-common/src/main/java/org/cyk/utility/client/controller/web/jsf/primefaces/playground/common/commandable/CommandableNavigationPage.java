package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.commandable;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.command.CommandableRenderType;
import org.cyk.utility.client.controller.component.command.CommandableRenderTypeButton;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.AbstractPageContainerManagedImpl;
import org.cyk.utility.string.StringConstant;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class CommandableNavigationPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Commandable Navigation";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		viewBuilder.getComponentsBuilder(Boolean.TRUE).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE)
		.addComponents(
			createCommandableBuilderNoNavigation(__inject__(CommandableRenderTypeButton.class))
			,createCommandableBuilderNavigateToIdentifier("commandableNavigationOutcomeView",null,__inject__(CommandableRenderTypeButton.class))
			,createCommandableBuilderNavigateToIdentifier("commandableNavigationOutcomeView",new Object[] {"p","v"},__inject__(CommandableRenderTypeButton.class))
			)
		
		;
		return viewBuilder;
	}
	
	private CommandableBuilder createCommandableBuilderNoNavigation(CommandableRenderType commandableRenderType) {
		return __inject__(CommandableBuilder.class).setRenderType(commandableRenderType).setName("No Navigation");
	}
	
	private CommandableBuilder createCommandableBuilderNavigateToIdentifier(Object identifier,Object[] parameters,CommandableRenderType commandableRenderType) {
		String parametersAsString = __inject__(ArrayHelper.class).isEmpty(parameters) ? StringConstant.EMPTY : (" : "+StringUtils.join(parameters,","));
		return __inject__(CommandableBuilder.class).setRenderType(commandableRenderType).setName("Navigate to "+identifier+parametersAsString)
				.setNavigationIdentifierAndParameters(identifier,parameters);
	}

}
