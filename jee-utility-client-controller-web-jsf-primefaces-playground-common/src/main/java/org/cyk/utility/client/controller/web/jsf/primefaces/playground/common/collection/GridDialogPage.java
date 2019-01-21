package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.collection;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class GridDialogPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Data";
	}
	
	@Override
	protected MenuBuilderMap __getMenuBuilderMap__() {
		return null;
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		Data data = new Data();
		
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		viewBuilder.addComponentBuilderByObjectByFieldNames(data, "string");
		viewBuilder.addComponentBuilderByObjectByFieldNames(data, "number");
		//viewBuilder.addComponentBuilderByObjectByFieldNames(data, "date");
		CommandableBuilder commandable =  (CommandableBuilder) viewBuilder.addComponentBuilderByObjectByMethodName(data, "submit");
		commandable.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setIsNotifyOnThrowableIsNull(Boolean.FALSE);
		
		//viewBuilder.getComponentsBuilder(Boolean.TRUE).addComponents(commandable);
		return viewBuilder;
	}
	
	/**/

}
