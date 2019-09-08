package org.cyk.utility.client.controller.web.jsf.primefaces.__internal__;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.AbstractDataImpl;
import org.cyk.utility.client.controller.data.AbstractFormDataImpl;
import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.client.controller.web.ComponentHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.notification.Notification;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.system.action.SystemActionCustom;
import org.cyk.utility.time.DurationBuilder;
import org.cyk.utility.time.DurationStringBuilder;
import org.omnifaces.util.Faces;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Named @ViewScoped @Getter @Setter
public class __InternalCheckerBuildTimePage__ extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer numberOfInputTexts;
	
	@Override
	protected void __listenPostConstruct__() {
		numberOfInputTexts = __inject__(NumberHelper.class).getInteger(Faces.getRequestParameter("numberOfInputTexts"));
		if(numberOfInputTexts == null || numberOfInputTexts >50)
			numberOfInputTexts = 50;
		super.__listenPostConstruct__();
		__isInternalLoggable__ = Boolean.TRUE;
		
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Page Build Time Checker. #InputTexts="+numberOfInputTexts;
	}
	
	/**/
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		Form form = __inject__(Form.class);
		form.setData(__inject__(Data.class));
		//form.getData().setFile(__inject__(File.class));
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		DurationBuilder durationBuilder = __inject__(DurationBuilder.class).setBeginToNow();
		for(Integer index = 1 ; index <= numberOfInputTexts ; index = index + 1) {
			viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.TRUE, "inputText"+index);
		}
		System.out.println("Add inputs builders : "+__inject__(DurationStringBuilder.class).setDurationBuilder(durationBuilder.setEndToNow()).execute().getOutput());
		
		durationBuilder.setBeginToNow();
		CommandableBuilder commandable = (CommandableBuilder) viewBuilder.addComponentBuilderByObjectByMethodName(form, "submit");
		commandable.setOutputProperty("update", __inject__(ComponentHelper.class).getGlobalMessagesTargetsIdentifiers());
		commandable.setCommandFunctionActionClass(SystemActionCustom.class);
		commandable.addCommandFunctionTryRunRunnable(new Runnable() {
			@Override
			public void run() {
				String message = form.getData().toString();
				__inject__(MessageRender.class).addNotifications(__inject__(Notification.class).setSummary(message)).execute();
			}
		});
		System.out.println("Add commandable builders : "+__inject__(DurationStringBuilder.class).setDurationBuilder(durationBuilder.setEndToNow()).execute().getOutput());
		return viewBuilder;
	}
	
	@Getter @Setter /*@Accessors(chain=true)*/ @ToString
	public static class Data extends AbstractDataImpl implements Serializable {
		private static final long serialVersionUID = 1L;

		@Input @InputString @InputStringLineOne @NotNull private String inputText1;
		@Input @InputString @InputStringLineOne @NotNull private String inputText2;
		@Input @InputString @InputStringLineOne @NotNull private String inputText3;
		@Input @InputString @InputStringLineOne @NotNull private String inputText4;
		@Input @InputString @InputStringLineOne @NotNull private String inputText5;
		@Input @InputString @InputStringLineOne @NotNull private String inputText6;
		@Input @InputString @InputStringLineOne @NotNull private String inputText7;
		@Input @InputString @InputStringLineOne @NotNull private String inputText8;
		@Input @InputString @InputStringLineOne @NotNull private String inputText9;
		@Input @InputString @InputStringLineOne @NotNull private String inputText10;
		
		@Input @InputString @InputStringLineOne @NotNull private String inputText11;
		@Input @InputString @InputStringLineOne @NotNull private String inputText12;
		@Input @InputString @InputStringLineOne @NotNull private String inputText13;
		@Input @InputString @InputStringLineOne @NotNull private String inputText14;
		@Input @InputString @InputStringLineOne @NotNull private String inputText15;
		@Input @InputString @InputStringLineOne @NotNull private String inputText16;
		@Input @InputString @InputStringLineOne @NotNull private String inputText17;
		@Input @InputString @InputStringLineOne @NotNull private String inputText18;
		@Input @InputString @InputStringLineOne @NotNull private String inputText19;
		@Input @InputString @InputStringLineOne @NotNull private String inputText20;
		
		@Input @InputString @InputStringLineOne @NotNull private String inputText21;
		@Input @InputString @InputStringLineOne @NotNull private String inputText22;
		@Input @InputString @InputStringLineOne @NotNull private String inputText23;
		@Input @InputString @InputStringLineOne @NotNull private String inputText24;
		@Input @InputString @InputStringLineOne @NotNull private String inputText25;
		@Input @InputString @InputStringLineOne @NotNull private String inputText26;
		@Input @InputString @InputStringLineOne @NotNull private String inputText27;
		@Input @InputString @InputStringLineOne @NotNull private String inputText28;
		@Input @InputString @InputStringLineOne @NotNull private String inputText29;
		@Input @InputString @InputStringLineOne @NotNull private String inputText30;
		
		@Input @InputString @InputStringLineOne @NotNull private String inputText31;
		@Input @InputString @InputStringLineOne @NotNull private String inputText32;
		@Input @InputString @InputStringLineOne @NotNull private String inputText33;
		@Input @InputString @InputStringLineOne @NotNull private String inputText34;
		@Input @InputString @InputStringLineOne @NotNull private String inputText35;
		@Input @InputString @InputStringLineOne @NotNull private String inputText36;
		@Input @InputString @InputStringLineOne @NotNull private String inputText37;
		@Input @InputString @InputStringLineOne @NotNull private String inputText38;
		@Input @InputString @InputStringLineOne @NotNull private String inputText39;
		@Input @InputString @InputStringLineOne @NotNull private String inputText40;
		
		@Input @InputString @InputStringLineOne @NotNull private String inputText41;
		@Input @InputString @InputStringLineOne @NotNull private String inputText42;
		@Input @InputString @InputStringLineOne @NotNull private String inputText43;
		@Input @InputString @InputStringLineOne @NotNull private String inputText44;
		@Input @InputString @InputStringLineOne @NotNull private String inputText45;
		@Input @InputString @InputStringLineOne @NotNull private String inputText46;
		@Input @InputString @InputStringLineOne @NotNull private String inputText47;
		@Input @InputString @InputStringLineOne @NotNull private String inputText48;
		@Input @InputString @InputStringLineOne @NotNull private String inputText49;
		@Input @InputString @InputStringLineOne @NotNull private String inputText50;
		
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Form extends AbstractFormDataImpl<Data> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
}
