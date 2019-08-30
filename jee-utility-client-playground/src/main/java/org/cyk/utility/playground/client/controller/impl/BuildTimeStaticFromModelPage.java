package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.AbstractDataImpl;
import org.cyk.utility.client.controller.data.AbstractFormDataImpl;
import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.notification.Notification;
import org.cyk.utility.number.NumberHelper;
import org.omnifaces.util.Faces;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Named @ViewScoped @Getter @Setter
public class BuildTimeStaticFromModelPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Build Time Static From Model";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		Form form = __inject__(Form.class);
		form.setData(__inject__(Data.class));
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		Integer numberOfInputs = __inject__(NumberHelper.class).getInteger(Faces.getRequestParameter("numberofinputs"),50);
		for(Integer index = 1 ; index <= numberOfInputs ; index = index + 1) {
			viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.TRUE, "inputText"+index);	
		}
		
		CommandableBuilder commandable = (CommandableBuilder) viewBuilder.addComponentBuilderByObjectByMethodName(form, "submit");
		commandable.addCommandFunctionTryRunRunnable(new Runnable() {
			@Override
			public void run() {
				String message = form.getData().toString();
				__inject__(MessageRender.class).addNotifications(__inject__(Notification.class).setSummary(message)).execute();
			}
		});
		commandable.getOutputProperties(Boolean.TRUE).setAjax(Boolean.FALSE);
		
		return viewBuilder;
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Data extends AbstractDataImpl implements Serializable {
		private static final long serialVersionUID = 1L;

		@Input @InputString @InputStringLineOne private String inputText1;
		@Input @InputString @InputStringLineOne private String inputText2;
		@Input @InputString @InputStringLineOne private String inputText3;
		@Input @InputString @InputStringLineOne private String inputText4;
		@Input @InputString @InputStringLineOne private String inputText5;
		@Input @InputString @InputStringLineOne private String inputText6;
		@Input @InputString @InputStringLineOne private String inputText7;
		@Input @InputString @InputStringLineOne private String inputText8;
		@Input @InputString @InputStringLineOne private String inputText9;
		@Input @InputString @InputStringLineOne private String inputText10;
		
		@Input @InputString @InputStringLineOne private String inputText11;
		@Input @InputString @InputStringLineOne private String inputText12;
		@Input @InputString @InputStringLineOne private String inputText13;
		@Input @InputString @InputStringLineOne private String inputText14;
		@Input @InputString @InputStringLineOne private String inputText15;
		@Input @InputString @InputStringLineOne private String inputText16;
		@Input @InputString @InputStringLineOne private String inputText17;
		@Input @InputString @InputStringLineOne private String inputText18;
		@Input @InputString @InputStringLineOne private String inputText19;
		@Input @InputString @InputStringLineOne private String inputText20;
		
		@Input @InputString @InputStringLineOne private String inputText21;
		@Input @InputString @InputStringLineOne private String inputText22;
		@Input @InputString @InputStringLineOne private String inputText23;
		@Input @InputString @InputStringLineOne private String inputText24;
		@Input @InputString @InputStringLineOne private String inputText25;
		@Input @InputString @InputStringLineOne private String inputText26;
		@Input @InputString @InputStringLineOne private String inputText27;
		@Input @InputString @InputStringLineOne private String inputText28;
		@Input @InputString @InputStringLineOne private String inputText29;
		@Input @InputString @InputStringLineOne private String inputText30;
		
		@Input @InputString @InputStringLineOne private String inputText31;
		@Input @InputString @InputStringLineOne private String inputText32;
		@Input @InputString @InputStringLineOne private String inputText33;
		@Input @InputString @InputStringLineOne private String inputText34;
		@Input @InputString @InputStringLineOne private String inputText35;
		@Input @InputString @InputStringLineOne private String inputText36;
		@Input @InputString @InputStringLineOne private String inputText37;
		@Input @InputString @InputStringLineOne private String inputText38;
		@Input @InputString @InputStringLineOne private String inputText39;
		@Input @InputString @InputStringLineOne private String inputText40;
		
		@Input @InputString @InputStringLineOne private String inputText41;
		@Input @InputString @InputStringLineOne private String inputText42;
		@Input @InputString @InputStringLineOne private String inputText43;
		@Input @InputString @InputStringLineOne private String inputText44;
		@Input @InputString @InputStringLineOne private String inputText45;
		@Input @InputString @InputStringLineOne private String inputText46;
		@Input @InputString @InputStringLineOne private String inputText47;
		@Input @InputString @InputStringLineOne private String inputText48;
		@Input @InputString @InputStringLineOne private String inputText49;
		@Input @InputString @InputStringLineOne private String inputText50;
		
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Form extends AbstractFormDataImpl<Data> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
}
