package org.cyk.utility.assertion;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.field.FieldValueGetter;

@Deprecated
public interface AssertionValue extends Objectable {

	String getName();
	AssertionValue setName(String name);
	
	FieldValueGetter getFieldValueGetter();
	AssertionValue setFieldValueGetter(FieldValueGetter fieldValueGetter);
	AssertionValue setFieldValueGetter(Object object,String...names);
	
	Object getValue();
	AssertionValue setValue(Object value);
	
	ValueSource getValueSource();
	AssertionValue setValueSource(ValueSource source);
	
	AssertionBuilder getParent();
	AssertionValue setParent(Object parent);
	
	Object getValueFromSource();
	
	/**/
	
	public static enum ValueSource {
		LITERAL,FIELD
	}
}
