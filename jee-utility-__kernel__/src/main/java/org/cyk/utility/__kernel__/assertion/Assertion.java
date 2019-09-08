package org.cyk.utility.__kernel__.assertion;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface Assertion extends Objectable {

	Boolean getIsAffirmation();
	Assertion setIsAffirmation(Boolean value);
	
	Boolean getValue();
	Assertion setValue(Boolean value);
	
	String getMessageWhenValueIsNotTrue();
	Assertion setMessageWhenValueIsNotTrue(String message);
	
	void throwIfValueIsNotTrue();
	
}
