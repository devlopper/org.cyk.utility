package org.cyk.utility.client.controller.component.input.choice;

public class InputChoiceManyAutoCompleteBuilderImpl extends AbstractInputChoiceManyBuilderImpl<InputChoiceManyAutoComplete> implements InputChoiceManyAutoCompleteBuilder {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setMaximumNumberOfChoice(InputChoice.MAXIMUM_NUMBER_OF_CHOICES_BY_QUERY);
	}
	
}
