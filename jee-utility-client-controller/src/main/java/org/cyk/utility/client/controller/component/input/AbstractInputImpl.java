package org.cyk.utility.client.controller.component.input;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.utility.client.controller.component.AbstractInputOutputImpl;
import org.cyk.utility.client.controller.component.output.OutputStringLabel;
import org.cyk.utility.client.controller.component.output.OutputStringMessage;

public abstract class AbstractInputImpl<T> extends AbstractInputOutputImpl<T> implements Input<T>,Serializable {
	private static final long serialVersionUID = 1L;

	protected Object valueObject;
	protected T initialValue;
	@Inject protected OutputStringLabel labelComponent;
	@Inject protected OutputStringMessage messageComponent;
	
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
		return value;
	}
	
	@Override
	public Input<T> setInitialValue(T initialValue) {
		this.initialValue = initialValue;
		return this;
	}
	
	@Override
	public OutputStringLabel getLabelComponent() {
		return labelComponent;
	}
	
	@Override
	public Input<T> setLabelComponent(OutputStringLabel labelComponent) {
		this.labelComponent = labelComponent;
		return this;
	}
	
	@Override
	public Input<T> setLabelComponentValue(Object value) {
		OutputStringLabel labelComponent = getLabelComponent();
		if(labelComponent == null)
			setLabelComponent(labelComponent = __inject__(OutputStringLabel.class));
		labelComponent.getProperties().setValue(value.toString());
		return this;
	}
	
	@Override
	public OutputStringMessage getMessageComponent() {
		return messageComponent;
	}
	
	@Override
	public Input<T> setMessageComponent(OutputStringMessage messageComponent) {
		this.messageComponent = messageComponent;
		return this;
	}
}
