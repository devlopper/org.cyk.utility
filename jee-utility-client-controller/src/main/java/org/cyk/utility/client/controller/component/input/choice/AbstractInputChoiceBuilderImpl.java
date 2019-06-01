package org.cyk.utility.client.controller.component.input.choice;

import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.client.controller.component.input.AbstractInputBuilderImpl;
import org.cyk.utility.object.Objects;

public abstract class AbstractInputChoiceBuilderImpl<INPUT extends InputChoice<CHOICE>,CHOICE> extends AbstractInputBuilderImpl<INPUT,CHOICE> implements InputChoiceBuilder<INPUT,CHOICE> {
	private static final long serialVersionUID = 1L;

	private Objects choices;
	private Class<? extends ChoicePropertyValueBuilder> choicePropertyValueBuilderClass;
	private Class<? extends ChoicesGetter> choicesGetterClass;
	private Integer maximumNumberOfChoice;
	
	@Override
	protected void __execute__(INPUT inputChoice, Object object, Field field) {
		super.__execute__(inputChoice, object, field);
		Class<? extends ChoicesGetter> choicesGetterClass = getChoicesGetterClass();
		if(choicesGetterClass == null)
			choicesGetterClass = ChoicesGetter.class;
		Class<? extends ChoicePropertyValueBuilder> choicePropertyValueBuilderClass = getChoicePropertyValueBuilderClass();
		inputChoice.setChoicePropertyValueBuilderClass(choicePropertyValueBuilderClass);
		Integer maximumNumberOfChoice = getMaximumNumberOfChoice();
		inputChoice.setMaximumNumberOfChoice(maximumNumberOfChoice);
		
		Objects choices = getChoices();
		if(choices == null) {
			ChoicesGetter choicesGetter = __inject__(choicesGetterClass);
			choices = choicesGetter.setFieldDeclaringClass(object.getClass()).setField(field).setRequest(getProperties().getRequest()).setContext(getProperties().getContext())
				.setMaximumNumberOfChoice(maximumNumberOfChoice)
				.execute().getOutput();
		}
		inputChoice.addChoices(choices.get());
	}
	
	@Override
	public Objects getChoices() {
		return choices;
	}
	
	@Override
	public Objects getChoices(Boolean injectIfNull) {
		return (Objects) __getInjectIfNull__(FIELD_CHOICES, injectIfNull);
	}
	
	@Override
	public InputChoiceBuilder<INPUT,CHOICE> setChoices(Objects choices) {
		this.choices = choices;
		return this;
	}
	
	@Override
	public InputChoiceBuilder<INPUT,CHOICE> addChoices(Collection<Object> choices) {
		getChoices(Boolean.TRUE).add(choices);
		return this;
	}
	
	@Override
	public InputChoiceBuilder<INPUT,CHOICE> addChoices(Object... choices) {
		getChoices(Boolean.TRUE).add(choices);
		return this;
	}
	
	@Override
	public Class<? extends ChoicePropertyValueBuilder> getChoicePropertyValueBuilderClass() {
		return choicePropertyValueBuilderClass;
	}
	
	@Override
	public InputChoiceBuilder<INPUT, CHOICE> setChoicePropertyValueBuilderClass(Class<? extends ChoicePropertyValueBuilder> choiceLabelBuilderClass) {
		this.choicePropertyValueBuilderClass = choiceLabelBuilderClass;
		return this;
	}
	
	@Override
	public Class<? extends ChoicesGetter> getChoicesGetterClass() {
		return choicesGetterClass;
	}
	
	@Override
	public InputChoiceBuilder<INPUT, CHOICE> setChoicesGetterClass(Class<? extends ChoicesGetter> choicesGetterClass) {
		this.choicesGetterClass = choicesGetterClass;
		return this;
	}
	
	@Override
	public Integer getMaximumNumberOfChoice() {
		return maximumNumberOfChoice;
	}
	
	@Override
	public InputChoiceBuilder<INPUT,CHOICE> setMaximumNumberOfChoice(Integer maximumNumberOfChoice) {
		this.maximumNumberOfChoice = maximumNumberOfChoice;
		return this;
	}
	
	/**/
	
	public static final String FIELD_CHOICES = "choices";
	
}
