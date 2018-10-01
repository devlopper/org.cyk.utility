package org.cyk.utility.assertion;

import java.util.Map;

import org.cyk.utility.collection.CollectionInstanceString;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.string.StringLocation;

public interface AssertionBuilderStringLocation extends AssertionBuilder {

	FieldValueGetter getFieldValueGetter();
	AssertionBuilderStringLocation setFieldValueGetter(FieldValueGetter fieldValueGetter);
	AssertionBuilderStringLocation setFieldValueGetter(Object object,String...names);
	
	String getValueName();
	AssertionBuilderStringLocation setValueName(String name);
	
	AssertionValue getAssertedValue();
	AssertionValue getAssertedValue(Boolean instanciateIfNull);
	AssertionBuilderStringLocation setAssertedValue(AssertionValue assertionValue);
	
	AssertionBuilderStringLocation setLocationStringsMap(Map<StringLocation,CollectionInstanceString> locationStringsMap);
	Map<StringLocation,CollectionInstanceString> getLocationStringsMap();
	Map<StringLocation,CollectionInstanceString> getLocationStringsMap(Boolean instanciateIfNull);
	
	AssertionBuilderStringLocation setLocationStrings(StringLocation location,CollectionInstanceString strings);
	CollectionInstanceString getLocationStrings(StringLocation location);
	CollectionInstanceString getLocationStrings(StringLocation location,Boolean injectIfNull);
	
	AssertionBuilderStringLocation setIsAffirmation(Boolean value);
	
}
