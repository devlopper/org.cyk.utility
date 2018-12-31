package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.commandable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.component.command.CommandFunction;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.event.EventBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.commandable.CommandableButtonDialogFrameworkDetailsPage.Model;
import org.cyk.utility.system.action.SystemActionCreate;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class CommandableButtonDialogFrameworkPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Commandable Button Dialog Framework";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		CommandableBuilder commandable = __inject__(CommandableBuilder.class).setName("Ouvrir le popup").setCommandFunctionActionClass(SystemActionCreate.class);
		commandable.addCommandFunctionTryRunRunnable(new Runnable() {
			@Override
			public void run() {
				Map<String,Object> options = new HashMap<String, Object>();
		        options.put("modal", true);
		        options.put("width", 640);
		        options.put("height", 340);
		        options.put("contentWidth", "100%");
		        options.put("contentHeight", "100%");
		        PrimeFaces.current().dialog().openDynamic("dialog", options, null);
			}
		});
		commandable.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setIsNotifyOnSuccess(Boolean.FALSE);
		
		EventBuilder event = __inject__(EventBuilder.class);
		event.setOutputProperties(__inject__(Properties.class));
		event.getOutputProperties().setEvent("dialogReturn");
		event.getOutputProperties().setDisabled("false");
		event.getOutputProperties().setAsync("true");
		event.getOutputProperties().setGlobal("true");
		event.getOutputProperties().setPartialSubmit("true");
		event.getOutputProperties().setResetValues("true");
		event.getOutputProperties().setIgnoreAutoUpdate("true");
		event.getOutputProperties().setImmediate("false");
		event.getOutputProperties().setSkipChildren("true");
		CommandFunction function = __inject__(CommandFunction.class);
		function.try_().getRun(Boolean.TRUE).addRunnables(new Runnable() {
			@Override
			public void run() {
				SelectEvent selectEvent = (SelectEvent) function.getProperties().getParameter();
				CommandableButtonDialogFrameworkDetailsPage.Model model = (Model) selectEvent.getObject();
				System.out.println("Données : "+model);
			}
		});
		event.getOutputProperties().setFunction(function);
		function.setAction(__inject__(SystemActionCreate.class));
		commandable.addEvents(event);
		
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		viewBuilder.getComponentsBuilder(Boolean.TRUE).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE)
		.addComponents(
			__inject__(OutputStringTextBuilder.class).setValue("Récap : ")
			,commandable
			)
		
		;
		return viewBuilder;
	}
	
}
