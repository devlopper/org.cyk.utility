package org.cyk.utility.client.controller.component.input;

public interface InputBoolean extends Input<Boolean> {

	InputBooleanValue getNullValue();
	InputBoolean setNullValue(InputBooleanValue nullValue);
	
	InputBooleanValue getTrueValue();
	InputBoolean setTrueValue(InputBooleanValue trueValue);
	
	InputBooleanValue getFalseValue();
	InputBoolean setFalseValue(InputBooleanValue falseValue);
}
