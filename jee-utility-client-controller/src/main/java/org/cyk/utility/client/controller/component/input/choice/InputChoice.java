package org.cyk.utility.client.controller.component.input.choice;

import java.util.Collection;
import java.util.List;

import org.cyk.utility.client.controller.component.input.Input;
import org.cyk.utility.__kernel__.object.Objects;

public interface InputChoice<CHOICE> extends Input<CHOICE> {

	Objects getChoices();
	Objects getChoices(Boolean injectIfNull);
	InputChoice<CHOICE> setChoices(Objects choices);
	InputChoice<CHOICE> addChoices(Collection<Object> choices);
	InputChoice<CHOICE> addChoices(Object...choices);
	
	Class<? extends ChoicePropertyValueBuilder> getChoicePropertyValueBuilderClass();
	InputChoice<CHOICE> setChoicePropertyValueBuilderClass(Class<? extends ChoicePropertyValueBuilder> choicePropertyValueBuilderClass);
	
	Object getChoiceProperty(Object choice,ChoiceProperty property);
	
	String getChoiceLabel(Object choice);
	Object getChoiceValue(Object choice);
	String getChoiceDescription(Object choice);
	Boolean getChoiceIsDisable(Object choice);
	Boolean getChoiceIsLabelEscaped(Object choice);
	
	List<Object> getChoicesByQuery(String query);
	
	Integer getMaximumNumberOfChoice();
	InputChoice<CHOICE> setMaximumNumberOfChoice(Integer maximumNumberOfChoice);
	
	ChoicesLayout getChoicesLayout();
	InputChoice<CHOICE> setChoicesLayout(ChoicesLayout choicesLayout);
	
	Integer MAXIMUM_NUMBER_OF_CHOICES_BY_QUERY = 10;
}
