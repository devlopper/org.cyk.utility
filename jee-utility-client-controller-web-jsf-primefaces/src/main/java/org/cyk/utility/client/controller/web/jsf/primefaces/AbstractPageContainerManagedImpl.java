package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.window.WindowBuilder;
import org.cyk.utility.client.controller.event.EventBuilder;
import org.cyk.utility.client.controller.event.EventName;

public abstract class AbstractPageContainerManagedImpl extends org.cyk.utility.client.controller.web.jsf.page.AbstractPageContainerManagedImpl implements PageContainerManaged,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected WindowBuilder __getWindowBuilder__() {
		WindowBuilder window =  super.__getWindowBuilder__();
		CommandableBuilder commandableBuilder = window.getDialog(Boolean.TRUE).getOkCommandable(Boolean.TRUE);
		String widgetVar = (String) window.getDialog(Boolean.TRUE).getOutputProperties().getWidgetVar();
		EventBuilder event = __inject__(EventBuilder.class).setName(EventName.CLICK).addScriptInstructions("PF('"+widgetVar+"').hide()");
		commandableBuilder.addEvents(event);
		return window;
	}
	
}
