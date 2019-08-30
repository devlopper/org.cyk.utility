package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.cache.Cache;
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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Named @ViewScoped @Getter @Setter
public class BuildTimeDynamicPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Build Time Dynamic";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		Form form = __inject__(Form.class);
		form.setData(__inject__(Data.class));
		//form.getData().setFile(__inject__(File.class));
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		for(Integer index = 1 ; index < 10 ; index = index + 1) {
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
		
		Cache cache = __inject__(Cache.class);
		cache.getProperties().setRegion("REGION");
		cache.getProperties().setKey("KEY");
		cache.getProperties().setDisabled(Boolean.FALSE);
		viewBuilder.getOutputProperties(Boolean.TRUE).setCache(cache);
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
		
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Form extends AbstractFormDataImpl<Data> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
}
