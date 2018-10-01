package org.cyk.utility.test;

import java.util.Map;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.collection.CollectionInstanceString;
import org.cyk.utility.string.StringLocation;

public interface TestExpectedString extends Objectable {

	TestExpectedString setLocationStringsMap(Map<StringLocation,CollectionInstanceString> locationStringsMap);
	Map<StringLocation,CollectionInstanceString> getLocationStringsMap();
	Map<StringLocation,CollectionInstanceString> getLocationStringsMap(Boolean instanciateIfNull);
	
	TestExpectedString setLocationStrings(StringLocation location,CollectionInstanceString strings);
	CollectionInstanceString getLocationStrings(StringLocation location);
	CollectionInstanceString getLocationStrings(StringLocation location,Boolean injectIfNull);
	
	void evaluate(String string);
}
