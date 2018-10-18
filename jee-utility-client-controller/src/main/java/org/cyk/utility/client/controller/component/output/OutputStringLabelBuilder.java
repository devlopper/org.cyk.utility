package org.cyk.utility.client.controller.component.output;

import org.cyk.utility.client.controller.component.input.InputBuilder;

public interface OutputStringLabelBuilder extends OutputStringBuilder<OutputStringLabel> {
	
	InputBuilder<?,?> getInputBuilder();
	OutputStringLabelBuilder setInputBuilder(InputBuilder<?,?> inputBuilder);
}
