package org.cyk.utility.assertion;

import org.cyk.utility.field.FieldValueGetter;

@Deprecated
public interface AssertionBuilderNull extends AssertionBuilder {

	FieldValueGetter getFieldValueGetter();
	AssertionBuilderNull setFieldValueGetter(FieldValueGetter fieldValueGetter);
	AssertionBuilderNull setFieldValueGetter(Object object,String...names);
	
	String getValueName();
	AssertionBuilderNull setValueName(String name);
	
	AssertionValue getAssertedValue();
	AssertionValue getAssertedValue(Boolean instanciateIfNull);
	AssertionBuilderNull setAssertedValue(AssertionValue assertionValue);
	
	AssertionBuilderNull setIsAffirmation(Boolean value);
	
}
