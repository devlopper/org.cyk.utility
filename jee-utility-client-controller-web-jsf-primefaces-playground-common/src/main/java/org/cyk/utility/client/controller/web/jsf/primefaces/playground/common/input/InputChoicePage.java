package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.input;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputChoice;
import org.cyk.utility.client.controller.component.annotation.InputChoiceMany;
import org.cyk.utility.client.controller.component.annotation.InputChoiceManyAutoComplete;
import org.cyk.utility.client.controller.component.annotation.InputChoiceManyCheckBox;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOne;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOneAutoComplete;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOneCombo;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineOneBuilder;
import org.cyk.utility.client.controller.component.input.choice.ChoicesLayoutResponsive;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.window.WindowBuilder;
import org.cyk.utility.client.controller.data.AbstractDataImpl;
import org.cyk.utility.client.controller.data.AbstractFormDataImpl;
import org.cyk.utility.client.controller.event.EventName;
import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.notification.Notification;
import org.primefaces.PrimeFaces;

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
	protected String __processWindowDialogOkCommandableGetUrl__(WindowBuilder window, CommandableBuilder commandable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		Form form = __inject__(Form.class);
		form.setData(__inject__(Data.class));
		//form.getData().setFile(__inject__(File.class));
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		InputStringLineOneBuilder text = (InputStringLineOneBuilder) viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.TRUE, "text");
		InputChoiceBuilder<?, ?> selectOneCombo = (InputChoiceBuilder<?, ?>) viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.TRUE, "selectOneCombo");
		InputChoiceBuilder<?, ?> selectOneComboDependent = (InputChoiceBuilder<?, ?>) viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.TRUE, "selectOneComboDependent");
		selectOneComboDependent.setIsGetChoices(Boolean.FALSE);
		
		selectOneCombo.addEvent(EventName.CHANGE, new Runnable() {
			@Override
			public void run() {
				Value value = (Value) selectOneCombo.getComponent().getValue();
				System.out.println("InputChoicePage.__getViewBuilder__().new Runnable() {...}.run() : "+value);
				selectOneComboDependent.getComponent().addChoices(__inject__(CollectionHelper.class).cast(Object.class, Arrays.asList(value)));
				text.getComponent().setValue(value.toString());
				PrimeFaces.current().ajax().update("form:"+text.getComponent().getIdentifier());
				PrimeFaces.current().ajax().update("form:"+selectOneComboDependent.getComponent().getIdentifier());
				//PrimeFaces.current().ajax().update("form:"+viewBuilder.getComponent().getComponents().getLayout().getIdentifier());
				//System.out.println("InputChoicePage.__getViewBuilder__().new Runnable() {...}.run() update : "+"form:"+text.getComponent().getIdentifier());
				//ValueChangeEvent valueChangeEvent = (ValueChangeEvent) inputChoiceBuilder.getComponent().getListenValueChangeCommand().getFunction().getProperties().getParameter();
				//System.out.println("InputChoicePage.__getViewBuilder__().new Runnable() {...}.run() : "+valueChangeEvent.getOldValue()+" / "+valueChangeEvent.getNewValue());
			}
		});
		
		viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.TRUE, "selectOneAutoComplete");
		InputChoiceBuilder<?, ?> selectManyAutoCheckBox = (InputChoiceBuilder<?, ?>) viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.TRUE, "selectManyAutoCheckBox");
		selectManyAutoCheckBox.setChoicesLayout(__inject__(ChoicesLayoutResponsive.class).setNumberOfColumns(5));
		
		InputChoiceBuilder<?, ?> selectManyAutoCheckBoxDependent = (InputChoiceBuilder<?, ?>) viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.TRUE, "selectManyAutoCheckBoxDependent");
		selectManyAutoCheckBoxDependent.setIsGetChoices(Boolean.FALSE);
		
		viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.TRUE, "selectManyAutoComplete");
		
		CommandableBuilder commandable = (CommandableBuilder) viewBuilder.addComponentBuilderByObjectByMethodName(form, "submit");
		commandable.addCommandFunctionTryRunRunnable(new Runnable() {
			@Override
			public void run() {
				String message = "selectOneCombo : "+form.getData().getSelectOneCombo()+"\r\nselectOneAutoComplete : "+form.getData().getSelectOneAutoComplete()
						+"\r\nselectManyAutoCheckBox : "+form.getData().selectManyAutoCheckBox+"\r\nselectManyAutoComplete : "+form.getData().selectManyAutoComplete;
				__inject__(MessageRender.class).addNotifications(__inject__(Notification.class).setSummary(message)).execute();
			}
		});
		commandable.getOutputProperties(Boolean.TRUE).setAjax(Boolean.FALSE);
		
		selectManyAutoCheckBox.addEvent(EventName.CHANGE, new Runnable() {
			@Override
			public void run() {
				@SuppressWarnings("unchecked")
				Collection<Value> values = (Collection<Value>) selectManyAutoCheckBox.getComponent().getValue();
				selectManyAutoCheckBoxDependent.getComponent().addChoices(__inject__(CollectionHelper.class).cast(Object.class, values));
			}
		},selectManyAutoCheckBoxDependent);
		
		return viewBuilder;
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Data extends AbstractDataImpl implements Serializable {
		private static final long serialVersionUID = 1L;

		@Input @InputStringLineOne
		private String text;
		
		@Input @InputChoice @InputChoiceOne @InputChoiceOneCombo
		private Value selectOneCombo;
		
		@Input @InputChoice @InputChoiceOne @InputChoiceOneCombo
		private Value selectOneComboDependent;
		
		@Input @InputChoice @InputChoiceOne @InputChoiceOneAutoComplete
		private Value selectOneAutoComplete;
		
		@Input @InputChoice @InputChoiceMany @InputChoiceManyCheckBox
		private List<Value> selectManyAutoCheckBox;
		
		@Input @InputChoice @InputChoiceMany @InputChoiceManyCheckBox
		private List<Value> selectManyAutoCheckBoxDependent;
		
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
	
	/*@Default
	public static class ChoiceLabelBuilderImpl extends AbstractChoicePropertyValueBuilderImpl implements ChoicePropertyValueBuilder,Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected String __execute__() throws Exception {
			VerySimpleEntity verySimpleEntity = (VerySimpleEntity) getObject();
			return verySimpleEntity == null ? ConstantEmpty.STRING : verySimpleEntity.getName();
		}
		
	}*/
	
}
