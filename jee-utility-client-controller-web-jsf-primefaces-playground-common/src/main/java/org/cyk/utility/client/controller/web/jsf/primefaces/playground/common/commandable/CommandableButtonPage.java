package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.commandable;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class CommandableButtonPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected WindowContainerManagedWindowBuilder __getWindowContainerManagedWindowBuilder__() {
		return null;
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Commandable Button";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		viewBuilder.getComponentsBuilder(Boolean.TRUE).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE)
		.addComponents(
			__inject__(CommandableBuilder.class).setName("No , client and server side , action")
			,__inject__(CommandableBuilder.class).setName("Custom client side only action").setOutputProperty(Properties.ON_CLICK, "alert('Run custom client side action.');")
			,__inject__(CommandableBuilder.class).setName("Custom server side only action").setCommandFunctionActionCustom("commandableButtonPageCustomServerSideOnly")
			/*
			,__inject__(CommandableBuilder.class).setName("Server Action,No Confirmation,Success")
			.setCommandFunctionActionClass(SystemActionCreate.class).setCommandFunctionData(new MyEntityImpl())
			,__inject__(CommandableBuilder.class).setName("Server Action,No Confirmation,Fail")
			.setCommandFunctionActionClass(SystemActionCreate.class).addCommandFunctionTryRunRunnable(new Runnable() {
				@Override
				public void run() {
					__inject__(ThrowableHelper.class).throwRuntimeException("STOP!!!");
				}
			})
			*/
			)
		
		;
		return viewBuilder;
	}
	
}
