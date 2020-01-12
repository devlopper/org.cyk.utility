package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.system.action.SystemActionCustom;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.client.controller.message.MessageRenderTypeDialog;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AutoCompleteEntity;
import org.cyk.utility.notification.NotificationBuilder;
import org.cyk.utility.notification.NotificationSeverityInformation;
import org.cyk.utility.playground.client.controller.entities.Namable;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class InputChoiceOneAutoCompletePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private AutoCompleteEntity<Namable> autoComplete;
	private Commandable commandable;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		autoComplete = new AutoCompleteEntity<Namable>(Namable.class);
		CommandableBuilder commandableBuilder = __inject__(CommandableBuilder.class);
		commandableBuilder.setName("Enregistrer").setCommandFunctionActionClass(SystemActionCustom.class).addCommandFunctionTryRunRunnable(
			new Runnable() {
				@Override
				public void run() {
					MessageRender messageRender = __inject__(MessageRender.class);
					messageRender.addNotificationBuilders(__inject__(NotificationBuilder.class)
							.setSummary("Value : "+autoComplete.getValue())
							.setSeverity(__inject__(NotificationSeverityInformation.class))
							);
					messageRender.addTypes(__inject__(MessageRenderTypeDialog.class));
					messageRender.execute();
				}
			}
		);
		//commandableBuilder.addUpdatables(__inject__(ComponentHelper.class).getGlobalMessagesTargetsIdentifiers());
		commandable = commandableBuilder.execute().getOutput();
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Input Choice One Auto Complete";
	}

}
