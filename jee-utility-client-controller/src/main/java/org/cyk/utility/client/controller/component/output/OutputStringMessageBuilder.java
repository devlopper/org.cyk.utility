package org.cyk.utility.client.controller.component.output;

import org.cyk.utility.client.controller.component.input.InputBuilder;

public interface OutputStringMessageBuilder extends OutputStringBuilder<OutputStringMessage> {

	InputBuilder<?,?> getInputBuilder();
	OutputStringMessageBuilder setInputBuilder(InputBuilder<?,?> inputBuilder);
}
