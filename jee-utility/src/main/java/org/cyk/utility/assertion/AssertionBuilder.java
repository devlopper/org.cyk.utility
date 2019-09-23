package org.cyk.utility.assertion;

import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;

@Deprecated
public interface AssertionBuilder extends FunctionWithPropertiesAsInput<Assertion> {

	Boolean getIsAffirmation();
	AssertionBuilder setIsAffirmation(Boolean value);
	
	Boolean getValue();
	AssertionBuilder setValue(Boolean value);
	
	AssertionBuilder setMessageWhenValueIsNotTrue(String message);
	String getMessageWhenValueIsNotTrue();
	
	Boolean getIsThrownWhenValueIsNotTrue();
	AssertionBuilder setIsThrownWhenValueIsNotTrue(Boolean IsThrownWhenValueIsNotTrue);
	
	AssertionBuilder setIdentifier(Object identifier);
	
}
