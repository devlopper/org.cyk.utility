package org.cyk.utility.client.controller.component.input;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.component.AbstractInputOutputBuilderImpl;
import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.command.CommandBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringLabelBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringMessageBuilder;

public abstract class AbstractInputBuilderImpl<INPUT extends Input<VALUE>,VALUE> extends AbstractInputOutputBuilderImpl<INPUT,VALUE> implements InputBuilder<INPUT,VALUE>,Serializable {
	private static final long serialVersionUID = 1L;

	private VALUE initialValue;
	private OutputStringLabelBuilder label;
	private OutputStringMessageBuilder message;
	private Boolean isEditable,isNullable;
	private CommandBuilder listenValueChangeCommand;
	
	public AbstractInputBuilderImpl() {
		addRoles(ComponentRole.INPUT);
	}
	
	@Override
	protected VALUE __getValue__(Object object, Field field, Object value) {
		return (VALUE) value;
	}
	
	@Override
	protected void __execute__(INPUT input, Object object, Field field) {
		super.__execute__(input, object, field);
		Boolean isNullable = getIsNullable();
		if(isNullable == null && field != null)
			isNullable = field.getAnnotation(javax.validation.constraints.NotNull.class) == null;
		input.setIsNullable(isNullable);
		
		OutputStringLabelBuilder label = getLabel();
		if(label!=null) {
			__setRequestAndContextAndUniformResourceLocatorMapOf__(label);
			input.setLabel(label.execute().getOutput());
		}
		
		OutputStringMessageBuilder message = getMessage();
		if(message!=null) {
			__setRequestAndContextAndUniformResourceLocatorMapOf__(message);
			input.setMessage(message.execute().getOutput());
		}
		
		CommandBuilder listenValueChangeCommand = getListenValueChangeCommand();
		if(listenValueChangeCommand == null) {
			
		}
		if(listenValueChangeCommand!=null)
			input.setListenValueChangeCommand(listenValueChangeCommand.execute().getOutput());
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
	public OutputStringLabelBuilder getLabel() {
		return label;
	}

	@Override
	public OutputStringLabelBuilder getLabel(Boolean injectIfNull) {
		if(label == null && Boolean.TRUE.equals(injectIfNull))
			label = __inject__(OutputStringLabelBuilder.class).setInputBuilder(this);
		return label;
	}

	@Override
	public InputBuilder<INPUT, VALUE> setLabel(OutputStringLabelBuilder labelBuilder) {
		this.label = labelBuilder;
		return this;
	}

	@Override
	public OutputStringMessageBuilder getMessage() {
		return message;
	}

	@Override
	public OutputStringMessageBuilder getMessage(Boolean injectIfNull) {
		if(message == null && Boolean.TRUE.equals(injectIfNull))
			message = __inject__(OutputStringMessageBuilder.class).setInputBuilder(this);
		return message;
	}

	@Override
	public InputBuilder<INPUT, VALUE> setMessage(OutputStringMessageBuilder messageBuilder) {
		this.message = messageBuilder;
		return this;
	}
	
	@Override
	public InputBuilder<INPUT, VALUE> setOutputPropertyRequired(Object required) {
		setOutputProperty(Properties.REQUIRED, required);
		return this;
	}
	
	@Override
	public Object getOutputPropertyRequired() {
		return getOutputProperty(Properties.REQUIRED);
	}
	
	@Override
	public InputBuilder<INPUT, VALUE> setOutputProperty(Object key, Object value) {
		return (InputBuilder<INPUT, VALUE>) super.setOutputProperty(key, value);
	}

	@Override
	public InputBuilder<INPUT, VALUE> setLabelValue(String value) {
		getLabel(Boolean.TRUE).setValue(value);
		return this;
	}
	
	@Override
	public Boolean getIsEditable() {
		return isEditable;
	}
	
	@Override
	public InputBuilder<INPUT, VALUE> setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
		return this;
	}
	
	@Override
	public Boolean getIsNullable() {
		return isNullable;
	}
	
	@Override
	public InputBuilder<INPUT, VALUE> setIsNullable(Boolean isNullable) {
		this.isNullable = isNullable;
		return this;
	}
	
	@Override
	public CommandBuilder getListenValueChangeCommand() {
		return listenValueChangeCommand;
	}
	
	@Override
	public CommandBuilder getListenValueChangeCommand(Boolean injectIfNull) {
		if(listenValueChangeCommand == null && Boolean.TRUE.equals(injectIfNull))
			listenValueChangeCommand = __inject__(CommandBuilder.class);
		return listenValueChangeCommand;
	}
	
	@Override
	public InputBuilder<INPUT, VALUE> setListenValueChangeCommand(CommandBuilder listenValueChangeCommand) {
		this.listenValueChangeCommand = listenValueChangeCommand;
		return this;
	}
	
	public static final String FIELD_LABEL = "label";
	public static final String FIELD_MESSAGE = "message";
	public static final String FIELD_LISTEN_VALUE_CHANGE_COMMAND = "listenValueChangeCommand";
	
}
