package org.cyk.utility.client.controller.component.input.choice;

import java.util.Collection;

import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.__kernel__.object.Objects;

public interface InputChoiceBuilder<INPUT extends InputChoice<CHOICE>,CHOICE> extends InputBuilder<INPUT,CHOICE> {

	Objects getChoices();
	Objects getChoices(Boolean injectIfNull);
	InputChoiceBuilder<INPUT,CHOICE> setChoices(Objects choices);
	InputChoiceBuilder<INPUT,CHOICE> addChoices(Collection<Object> choices);
	InputChoiceBuilder<INPUT,CHOICE> addChoices(Object...choices);
	
	Class<? extends ChoicesGetter> getChoicesGetterClass();
	InputChoiceBuilder<INPUT,CHOICE> setChoicesGetterClass(Class<? extends ChoicesGetter> choicesGetterClass);
	
	Integer getMaximumNumberOfChoice();
	InputChoiceBuilder<INPUT,CHOICE> setMaximumNumberOfChoice(Integer maximumNumberOfChoice);
	
	Class<? extends ChoicePropertyValueBuilder> getChoicePropertyValueBuilderClass();
	InputChoiceBuilder<INPUT,CHOICE> setChoicePropertyValueBuilderClass(Class<? extends ChoicePropertyValueBuilder> choicePropertyValueBuilderClass);
	
	Boolean getIsGetChoices();
	InputChoiceBuilder<INPUT,CHOICE> setIsGetChoices(Boolean isGetChoices);
	
	ChoicesLayout getChoicesLayout();
	InputChoiceBuilder<INPUT,CHOICE> setChoicesLayout(ChoicesLayout choicesLayout);
	
}
