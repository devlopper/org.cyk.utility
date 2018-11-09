package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.commandable;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.crud.MyEntityDataImpl;
import org.cyk.utility.system.action.SystemActionCreate;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class CommandableButtonPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Commandable Button";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		CommandableBuilder commandableBuilder = __inject__(CommandableBuilder.class).setName("Server Action,No Confirmation").setCommandFunctionActionClass(SystemActionCreate.class)
				.addCommandFunctionTryRunRunnable(new Runnable() {	
					@Override
					public void run() {
						System.out.println("Server Action , No Confirmation Done.");
					}
				});
		
		commandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).getProperties().setData(new MyEntityDataImpl());
		
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		viewBuilder.getComponentsBuilder(Boolean.TRUE).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE)
		.addComponents(
			__inject__(CommandableBuilder.class).setName("No Action")
			,commandableBuilder)
		
		;
		return viewBuilder;
	}

}
