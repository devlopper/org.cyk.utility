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
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.DataTableEntity;
import org.cyk.utility.notification.NotificationBuilder;
import org.cyk.utility.notification.NotificationSeverityInformation;
import org.cyk.utility.playground.client.controller.entities.Namable;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class InputChoiceManyDataTablePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private DataTableEntity<Namable> dataTable;
	private Commandable commandable;
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Input Choice Many DataTable";
	}
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		dataTable = new DataTableEntity<Namable>();
		
		CommandableBuilder commandableBuilder = __inject__(CommandableBuilder.class);
		commandableBuilder.setName("Enregistrer").setCommandFunctionActionClass(SystemActionCustom.class).addCommandFunctionTryRunRunnable(
			new Runnable() {
				@Override
				public void run() {
					MessageRender messageRender = __inject__(MessageRender.class);
					messageRender.addNotificationBuilders(__inject__(NotificationBuilder.class)
							.setSummary("Value : "+dataTable.getValue())
							.setSeverity(__inject__(NotificationSeverityInformation.class))
							);
					messageRender.addTypes(__inject__(MessageRenderTypeDialog.class));
					messageRender.execute();
				}
			}
		);
		commandable = commandableBuilder.execute().getOutput();
		
		commandableBuilder = __inject__(CommandableBuilder.class);
		commandableBuilder.setName("Enregistrer").setCommandFunctionActionClass(SystemActionCustom.class).addCommandFunctionTryRunRunnable(
			new Runnable() {
				@Override
				public void run() {
					System.out.println(
							"InputChoiceManyDataTablePage.__listenPostConstruct__().new Runnable() {...}.run()");
				}
			}
		);
		//dataTable.getDialog().setCommandable(commandableBuilder.execute().getOutput());
	}
	
}
