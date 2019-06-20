package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.input;

import java.io.Serializable;
import java.time.LocalDate;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputDate;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.AbstractDataImpl;
import org.cyk.utility.client.controller.data.AbstractFormDataImpl;
import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.notification.Notification;
import org.cyk.utility.system.action.SystemActionCustom;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Named @ViewScoped @Getter @Setter
public class InputDatePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Input Date";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		Form form = __inject__(Form.class);
		form.setData(__inject__(Data.class));
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.TRUE, "date01");
		
		CommandableBuilder commandable = (CommandableBuilder) viewBuilder.addComponentBuilderByObjectByMethodName(form, "submit");
		commandable.addCommandFunctionTryRunRunnable(new Runnable() {
			@Override
			public void run() {
				String message = "Date 01 : "+form.getData().getDate01();
				__inject__(MessageRender.class).addNotifications(__inject__(Notification.class).setSummary(message)).execute();
			}
		});
		commandable.getCommand().getFunction().setAction(__inject__(SystemActionCustom.class));
		
		return viewBuilder;
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Data extends AbstractDataImpl implements Serializable {
		private static final long serialVersionUID = 1L;

		@Input @InputDate
		private LocalDate date01;
		
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Form extends AbstractFormDataImpl<Data> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
}
