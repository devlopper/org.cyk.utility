package org.cyk.utility.client.controller.component.input;

import org.cyk.utility.client.controller.component.InputOutputBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringLabelBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringMessageBuilder;

public interface InputBuilder<INPUT extends Input<VALUE>,VALUE> extends InputOutputBuilder<INPUT,VALUE> {

	VALUE getInitialValue();
	InputBuilder<INPUT,VALUE> setInitialValue(VALUE initialValue);
	
	OutputStringLabelBuilder getLabel();
	OutputStringLabelBuilder getLabel(Boolean injectIfNull);
	InputBuilder<INPUT,VALUE> setLabel(OutputStringLabelBuilder labelBuilder);
	InputBuilder<INPUT,VALUE> setLabelValue(String value);
	
	OutputStringMessageBuilder getMessage();
	OutputStringMessageBuilder getMessage(Boolean injectIfNull);
	InputBuilder<INPUT,VALUE> setMessage(OutputStringMessageBuilder messageBuilder);

	@Override InputBuilder<INPUT,VALUE> setOutputProperty(Object key, Object value);
	
	Object getOutputPropertyRequired();
	InputBuilder<INPUT,VALUE> setOutputPropertyRequired(Object required);
}
