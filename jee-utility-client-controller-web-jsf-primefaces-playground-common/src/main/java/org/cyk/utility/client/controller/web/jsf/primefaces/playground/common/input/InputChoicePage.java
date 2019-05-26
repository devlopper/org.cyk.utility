package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.input;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputChoice;
import org.cyk.utility.client.controller.component.annotation.InputChoiceMany;
import org.cyk.utility.client.controller.component.annotation.InputChoiceManyAutoComplete;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOne;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOneAutoComplete;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOneCombo;
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
public class InputChoicePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Input Choice";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		Form form = __inject__(Form.class);
		form.setData(__inject__(Data.class));
		//form.getData().setFile(__inject__(File.class));
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.TRUE, "selectOneCombo");
		viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.TRUE, "selectOneAutoComplete");
		viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.TRUE, "selectManyAutoComplete");
		
		CommandableBuilder commandable = (CommandableBuilder) viewBuilder.addComponentBuilderByObjectByMethodName(form, "submit");
		commandable.addCommandFunctionTryRunRunnable(new Runnable() {
			@Override
			public void run() {
				String message = "selectOneCombo : "+form.getData().getSelectOneCombo()+"\r\nselectOneAutoComplete : "+form.getData().getSelectOneAutoComplete()
						+"\r\nselectManyAutoComplete : "+form.getData().selectManyAutoComplete;
				__inject__(MessageRender.class).addNotifications(__inject__(Notification.class).setSummary(message)).execute();
			}
		});
		commandable.getOutputProperties(Boolean.TRUE).setAjax(Boolean.FALSE);
		return viewBuilder;
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Data extends AbstractDataImpl implements Serializable {
		private static final long serialVersionUID = 1L;

		@Input @InputChoice @InputChoiceOne @InputChoiceOneCombo
		private Value selectOneCombo;
		
		@Input @InputChoice @InputChoiceOne @InputChoiceOneAutoComplete
		private Value selectOneAutoComplete;
		
		@Input @InputChoice @InputChoiceMany @InputChoiceManyAutoComplete
		private List<Value> selectManyAutoComplete;
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Form extends AbstractFormDataImpl<Data> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
	public static enum Value {
		V01,V02,V03,V04,V05,V06,V07,V08,V09,V10
		,V11,V12,V13,V14,V15,V16,V17,V18,V19,V20
		,V21,V22,V23,V24,V25,V26,V27,V28,V29,V30
		,V31,V32,V33,V34,V35,V36,V37,V38,V39,V40
		,V41,V42,V43,V44,V45,V46,V47,V48,V49,V50
		,V51,V52,V53,V54,V55,V56,V57,V58,V59,V60
	}
	
}
