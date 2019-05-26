package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.input;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.client.controller.component.annotation.Commandable;
import org.cyk.utility.client.controller.component.annotation.CommandableButton;
import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputChoice;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOne;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOneCombo;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineMany;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.component.input.choice.AbstractChoicePropertyValueBuilderImpl;
import org.cyk.utility.client.controller.component.input.choice.ChoicePropertyValueBuilder;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntity;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.system.action.SystemActionCreate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Named @ViewScoped @Getter @Setter
public class InputPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Input";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		Entity entity = new Entity();
		
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		viewBuilder.addComponentBuilderByObjectByFieldNames(entity, "name");
		viewBuilder.addComponentBuilderByObjectByFieldNames(entity, "details");
		viewBuilder.addComponentBuilderByObjectByFieldNames(entity, "enumeration01");
		viewBuilder.addComponentBuilderByObjectByFieldNames(entity, "verySimpleEntity01");
		InputChoiceBuilder<?, ?> inputChoiceBuilder = (InputChoiceBuilder<?, ?>) viewBuilder.addComponentBuilderByObjectByFieldNames(entity, "verySimpleEntity02");
		inputChoiceBuilder.setChoicePropertyValueBuilderClass(ChoiceLabelBuilderImpl01.class);
		viewBuilder.addComponentBuilderByObjectByFieldNames(entity, "verySimpleEntity03");
		
		viewBuilder.addComponentBuilderByObjectByMethodName(entity, "submit");
		
		return viewBuilder;
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Entity {
		
		@Input @InputString @InputStringLineOne
		private String name;
		
		@Input @InputString @InputStringLineMany
		private String details;
		
		@Input @InputChoice @InputChoiceOne @InputChoiceOneCombo
		private Enumeration01 enumeration01;
		
		@Input @InputChoice @InputChoiceOne @InputChoiceOneCombo
		private VerySimpleEntity verySimpleEntity01;
		
		@Input @InputChoice @InputChoiceOne @InputChoiceOneCombo
		private VerySimpleEntity verySimpleEntity02;
		
		@Input @InputChoice(choicePropertyValueBuilderClass=ChoiceLabelBuilderImpl02.class) @InputChoiceOne @InputChoiceOneCombo
		private VerySimpleEntity verySimpleEntity03;
		
		@Commandable(systemActionClass=SystemActionCreate.class) @CommandableButton
		public void submit() {
			System.out.println("InputPage.Entity.submit()");
			System.out.println(this);
		}
		
	}
	
	public static enum Enumeration01 {
		V01
		,V02
		,V03
		,V04
		,V05
	}
	
	public static class ChoiceLabelBuilderImpl01 extends AbstractChoicePropertyValueBuilderImpl implements ChoicePropertyValueBuilder,Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected String __execute__() throws Exception {
			VerySimpleEntity verySimpleEntity = (VerySimpleEntity) getObject();
			return verySimpleEntity == null ? ConstantEmpty.STRING : verySimpleEntity.getName();
		}
		
	}
	
	public static class ChoiceLabelBuilderImpl02 extends AbstractChoicePropertyValueBuilderImpl implements ChoicePropertyValueBuilder,Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected String __execute__() throws Exception {
			VerySimpleEntity verySimpleEntity = (VerySimpleEntity) getObject();
			return verySimpleEntity == null ? ConstantEmpty.STRING : verySimpleEntity.getCode()+":::"+verySimpleEntity.getName();
		}
		
	}
}
