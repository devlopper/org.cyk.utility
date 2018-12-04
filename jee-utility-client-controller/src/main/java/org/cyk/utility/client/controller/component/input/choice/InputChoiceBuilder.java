package org.cyk.utility.client.controller.component.input.choice;

import java.util.Collection;

import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.object.Objects;

public interface InputChoiceBuilder<INPUT extends InputChoice<CHOICE>,CHOICE> extends InputBuilder<INPUT,CHOICE> {

	Objects getChoices();
	Objects getChoices(Boolean injectIfNull);
	InputChoiceBuilder<INPUT,CHOICE> setChoices(Objects choices);
	InputChoiceBuilder<INPUT,CHOICE> addChoices(Collection<Object> choices);
	InputChoiceBuilder<INPUT,CHOICE> addChoices(Object...choices);
	
	Class<? extends ChoicePropertyValueBuilder> getChoiceLabelBuilderClass();
	InputChoiceBuilder<INPUT,CHOICE> setChoiceLabelBuilderClass(Class<? extends ChoicePropertyValueBuilder> choiceLabelBuilderClass);
	
}
