package org.cyk.utility.client.controller.component.output;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.string.Case;

public class OutputStringLabelBuilderImpl extends AbstractOutputStringBuilderImpl<OutputStringLabel> implements OutputStringLabelBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private InputBuilder<?, ?> inputBuilder;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		valueInternalizationCase = Case.FIRST_CHARACTER_UPPER;
	}
	
	@Override
	public InputBuilder<?, ?> getInputBuilder() {
		return inputBuilder;
	}

	@Override
	public OutputStringLabelBuilder setInputBuilder(InputBuilder<?, ?> inputBuilder) {
		this.inputBuilder = inputBuilder;
		return this;
	}

}
