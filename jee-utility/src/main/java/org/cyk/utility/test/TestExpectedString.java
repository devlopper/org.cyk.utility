package org.cyk.utility.test;

import java.util.Map;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.string.StringLocation;
import org.cyk.utility.string.Strings;

public interface TestExpectedString extends Objectable {

	TestExpectedString setLocationStringsMap(Map<StringLocation,Strings> locationStringsMap);
	Map<StringLocation,Strings> getLocationStringsMap();
	Map<StringLocation,Strings> getLocationStringsMap(Boolean instanciateIfNull);
	
	TestExpectedString setLocationStrings(StringLocation location,Strings strings);
	Strings getLocationStrings(StringLocation location);
	Strings getLocationStrings(StringLocation location,Boolean injectIfNull);
	
	void evaluate(String string);
}
