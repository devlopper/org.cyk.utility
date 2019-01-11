package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.commandable;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.entities.myentity.MyEntityImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.throwable.ThrowableHelper;

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
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		viewBuilder.getComponentsBuilder(Boolean.TRUE).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE)
		.addComponents(
			__inject__(CommandableBuilder.class).setName("No Action")
			,__inject__(CommandableBuilder.class).setName("Server Action,No Confirmation,Success")
			.setCommandFunctionActionClass(SystemActionCreate.class).setCommandFunctionData(new MyEntityImpl())
			,__inject__(CommandableBuilder.class).setName("Server Action,No Confirmation,Fail")
			.setCommandFunctionActionClass(SystemActionCreate.class).addCommandFunctionTryRunRunnable(new Runnable() {
				@Override
				public void run() {
					__inject__(ThrowableHelper.class).throwRuntimeException("STOP!!!");
				}
			})			
			)
		
		;
		return viewBuilder;
	}
	
}
