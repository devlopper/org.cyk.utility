package org.cyk.utility.client.controller.component.input;

import java.io.Serializable;

public abstract class AbstractInputBooleanImpl extends AbstractInputImpl<Boolean> implements InputBoolean,Serializable {
	private static final long serialVersionUID = 1L;
	
	private InputBooleanValue nullValue,trueValue,falseValue;
	
	@Override
	public InputBooleanValue getNullValue() {
		return nullValue;
	}
	
	@Override
	public InputBoolean setNullValue(InputBooleanValue nullValue) {
		this.nullValue = nullValue;
		return this;
	}
	
	@Override
	public InputBooleanValue getTrueValue() {
		return trueValue;
	}
	
	@Override
	public InputBoolean setTrueValue(InputBooleanValue trueValue) {
		this.trueValue = trueValue;
		return this;
	}
	
	@Override
	public InputBooleanValue getFalseValue() {
		return falseValue;
	}
	
	@Override
	public InputBoolean setFalseValue(InputBooleanValue falseValue) {
		this.falseValue = falseValue;
		return this;
	}
	
}
