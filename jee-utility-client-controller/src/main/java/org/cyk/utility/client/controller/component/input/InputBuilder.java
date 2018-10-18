package org.cyk.utility.client.controller.component.input;

import org.cyk.utility.client.controller.component.InputOutputBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringLabelBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringMessageBuilder;

public interface InputBuilder<INPUT extends Input<VALUE>,VALUE> extends InputOutputBuilder<INPUT,VALUE> {

	VALUE getInitialValue();
	InputBuilder<INPUT,VALUE> setInitialValue(VALUE initialValue);
	
	OutputStringLabelBuilder getLabelBuilder();
	OutputStringLabelBuilder getLabelBuilder(Boolean injectIfNull);
	InputBuilder<INPUT,VALUE> setLabelBuilder(OutputStringLabelBuilder labelBuilder);
	
	OutputStringMessageBuilder getMessageBuilder();
	OutputStringMessageBuilder getMessageBuilder(Boolean injectIfNull);
	InputBuilder<INPUT,VALUE> setMessageBuilder(OutputStringMessageBuilder messageBuilder);
	
}
