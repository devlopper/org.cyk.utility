package org.cyk.utility.client.controller.component.input;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractInputOutputBuilderImpl;
import org.cyk.utility.client.controller.component.output.OutputStringLabelBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringMessageBuilder;

public abstract class AbstractInputBuilderImpl<INPUT extends Input<VALUE>,VALUE> extends AbstractInputOutputBuilderImpl<INPUT,VALUE> implements InputBuilder<INPUT,VALUE>,Serializable {
	private static final long serialVersionUID = 1L;

	private VALUE initialValue;
	private OutputStringLabelBuilder labelBuilder;
	private OutputStringMessageBuilder messageBuilder;
	
	@Override
	protected void __execute__(INPUT input) {
		super.__execute__(input);
		OutputStringLabelBuilder labelBuilder = getLabelBuilder();
		if(labelBuilder!=null) {
			input.setLabel(labelBuilder.execute().getOutput());
		}
		
		OutputStringMessageBuilder messageBuilder = getMessageBuilder();
		if(messageBuilder!=null) {
			input.setMessage(messageBuilder.execute().getOutput());
		}
		/*
		input.getLabel().setWidths(1, 1, 1, 1, 12);
		input.setWidths(4, 4, 4, 4, 12);
		input.getMessage().setWidths(1, 1, 1, 1, 12);
		*/
	}
	
	@Override
	public VALUE getInitialValue() {
		return initialValue;
	}

	@Override
	public InputBuilder<INPUT, VALUE> setInitialValue(VALUE initialValue) {
		this.initialValue = initialValue;
		return this;
	}

	@Override
	public OutputStringLabelBuilder getLabelBuilder() {
		return labelBuilder;
	}

	@Override
	public OutputStringLabelBuilder getLabelBuilder(Boolean injectIfNull) {
		return ((OutputStringLabelBuilder) __getInjectIfNull__(FIELD_LABEL_BUILDER, injectIfNull)).setInputBuilder(this);
	}

	@Override
	public InputBuilder<INPUT, VALUE> setLabelBuilder(OutputStringLabelBuilder labelBuilder) {
		this.labelBuilder = labelBuilder;
		return this;
	}

	@Override
	public OutputStringMessageBuilder getMessageBuilder() {
		return messageBuilder;
	}

	@Override
	public OutputStringMessageBuilder getMessageBuilder(Boolean injectIfNull) {
		return ((OutputStringMessageBuilder) __getInjectIfNull__(FIELD_MESSAGE_BUILDER, injectIfNull)).setInputBuilder(this);
	}

	@Override
	public InputBuilder<INPUT, VALUE> setMessageBuilder(OutputStringMessageBuilder messageBuilder) {
		this.messageBuilder = messageBuilder;
		return this;
	}

	public static final String FIELD_LABEL_BUILDER = "labelBuilder";
	public static final String FIELD_MESSAGE_BUILDER = "messageBuilder";
	
}
