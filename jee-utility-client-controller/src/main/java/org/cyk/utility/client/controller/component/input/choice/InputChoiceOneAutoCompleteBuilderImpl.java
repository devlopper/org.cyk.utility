package org.cyk.utility.client.controller.component.input.choice;

public class InputChoiceOneAutoCompleteBuilderImpl extends AbstractInputChoiceOneBuilderImpl<InputChoiceOneAutoComplete> implements InputChoiceOneAutoCompleteBuilder {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setMaximumNumberOfChoice(InputChoice.MAXIMUM_NUMBER_OF_CHOICES_BY_QUERY);
	}
	
}
