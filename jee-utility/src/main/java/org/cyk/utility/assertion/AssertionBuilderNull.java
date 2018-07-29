package org.cyk.utility.assertion;

import org.cyk.utility.field.FieldValueGetter;

public interface AssertionBuilderNull extends AssertionBuilder {

	FieldValueGetter getFieldValueGetter();
	AssertionBuilderNull setFieldValueGetter(FieldValueGetter fieldValueGetter);
	AssertionBuilderNull setFieldValueGetter(Object object,String...names);
	
	String getValueName();
	AssertionBuilderNull setValueName(String name);
	
	AssertionBuilderNull setIsAffirmation(Boolean value);
	
}
