package org.cyk.utility.client.controller.component.input.choice;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.component.input.AbstractInputImpl;
import org.cyk.utility.object.Objects;

public abstract class AbstractInputChoiceImpl<CHOICE> extends AbstractInputImpl<CHOICE> implements InputChoice<CHOICE>,Serializable {
	private static final long serialVersionUID = 1L;

	private Objects choices;
	
	@Override
	public Objects getChoices() {
		return choices;
	}
	
	@Override
	public Objects getChoices(Boolean injectIfNull) {
		return (Objects) __getInjectIfNull__(FIELD_CHOICES, injectIfNull);
	}
	
	@Override
	public InputChoice<CHOICE> setChoices(Objects choices) {
		this.choices = choices;
		return this;
	}
	
	@Override
	public InputChoice<CHOICE> addChoices(Collection<Object> choices) {
		getChoices(Boolean.TRUE).add(choices);
		return this;
	}
	
	@Override
	public InputChoice<CHOICE> addChoices(Object... choices) {
		getChoices(Boolean.TRUE).add(choices);
		return this;
	}
	
	/**/
	
	public static final String FIELD_CHOICES = "choices";
}
