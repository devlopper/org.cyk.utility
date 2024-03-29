package org.cyk.utility.assertion;

import java.util.Map;

import org.cyk.utility.__kernel__.string.StringLocation;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.field.FieldValueGetter;

@Deprecated
public interface AssertionBuilderStringLocation extends AssertionBuilder {

	FieldValueGetter getFieldValueGetter();
	AssertionBuilderStringLocation setFieldValueGetter(FieldValueGetter fieldValueGetter);
	AssertionBuilderStringLocation setFieldValueGetter(Object object,String...names);
	
	String getValueName();
	AssertionBuilderStringLocation setValueName(String name);
	
	AssertionValue getAssertedValue();
	AssertionValue getAssertedValue(Boolean instanciateIfNull);
	AssertionBuilderStringLocation setAssertedValue(AssertionValue assertionValue);
	
	AssertionBuilderStringLocation setLocationStringsMap(Map<StringLocation,Strings> locationStringsMap);
	Map<StringLocation,Strings> getLocationStringsMap();
	Map<StringLocation,Strings> getLocationStringsMap(Boolean instanciateIfNull);
	
	AssertionBuilderStringLocation setLocationStrings(StringLocation location,Strings strings);
	Strings getLocationStrings(StringLocation location);
	Strings getLocationStrings(StringLocation location,Boolean injectIfNull);
	
	AssertionBuilderStringLocation setIsAffirmation(Boolean value);
	
}
