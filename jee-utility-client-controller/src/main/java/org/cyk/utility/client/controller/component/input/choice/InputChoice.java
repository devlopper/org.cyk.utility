package org.cyk.utility.client.controller.component.input.choice;

import java.util.Collection;

import org.cyk.utility.client.controller.component.input.Input;
import org.cyk.utility.object.Objects;

public interface InputChoice<CHOICE> extends Input<CHOICE> {

	Objects getChoices();
	Objects getChoices(Boolean injectIfNull);
	InputChoice<CHOICE> setChoices(Objects choices);
	InputChoice<CHOICE> addChoices(Collection<Object> choices);
	InputChoice<CHOICE> addChoices(Object...choices);
	
}
