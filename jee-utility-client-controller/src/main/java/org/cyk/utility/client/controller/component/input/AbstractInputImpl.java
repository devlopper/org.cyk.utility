package org.cyk.utility.client.controller.component.input;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.utility.client.controller.component.AbstractInputOutputImpl;
import org.cyk.utility.client.controller.component.command.Command;
import org.cyk.utility.client.controller.component.output.OutputStringLabel;
import org.cyk.utility.client.controller.component.output.OutputStringMessage;

public abstract class AbstractInputImpl<T> extends AbstractInputOutputImpl<T> implements Input<T>,Serializable {
	private static final long serialVersionUID = 1L;

	protected Object valueObject;
	protected T initialValue;
	private Boolean isNullable;
	@Inject protected OutputStringLabel label;
	@Inject protected OutputStringMessage message;
	private Command listenValueChangeCommand;
	
	@Override
	public Object getValueObject() {
		return valueObject;
	}
	
	@Override
	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}
	
	@Override
	public T getInitialValue() {
		return initialValue;
	}
	
	@Override
	public Input<T> setInitialValue(T initialValue) {
		this.initialValue = initialValue;
		return this;
	}
	
	@Override
	public OutputStringLabel getLabel() {
		return label;
	}
	
	@Override
	public Input<T> setLabel(OutputStringLabel label) {
		this.label = label;
		return this;
	}
	
	@Override
	public OutputStringMessage getMessage() {
		return message;
	}
	
	@Override
	public Input<T> setMessage(OutputStringMessage message) {
		this.message = message;
		return this;
	}
	
	@Override
	public Boolean getIsNullable() {
		return isNullable;
	}
	
	@Override
	public Input<T> setIsNullable(Boolean isNullable) {
		this.isNullable = isNullable;
		return this;
	}
	
	@Override
	public Command getListenValueChangeCommand() {
		return listenValueChangeCommand;
	}
	
	@Override
	public Input<T> setListenValueChangeCommand(Command listenValueChangeCommand) {
		this.listenValueChangeCommand = listenValueChangeCommand;
		return this;
	}
}
