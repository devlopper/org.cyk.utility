package org.cyk.utility.client.controller.entities.myentity;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.window.WindowBuilder;
import org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilder;
import org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilderGetter;
import org.cyk.utility.client.controller.event.EventBuilder;
import org.cyk.utility.client.controller.event.EventName;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class MyEntityEditPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected WindowContainerManagedWindowBuilder __getWindowContainerManagedWindowBuilder__() {
		return __inject__(WindowContainerManagedWindowBuilderGetter.class).execute().getOutput();
	}
	
	@Override
	protected WindowBuilder __getWindowBuilder__() {
		WindowBuilder window =  super.__getWindowBuilder__();
		CommandableBuilder commandableBuilder = window.getDialog(Boolean.TRUE).getOkCommandable(Boolean.TRUE);
		String script = "window.location.href="+"'http://www.google.com'";
		EventBuilder event = __inject__(EventBuilder.class).setName(EventName.CLICK).addScriptInstructions(script);
		commandableBuilder.addEvents(event);
		return window;
	}
	
}
