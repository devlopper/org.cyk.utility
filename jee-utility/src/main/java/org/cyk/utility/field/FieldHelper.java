package org.cyk.utility.field;

import java.util.Collection;

import org.cyk.utility.helper.Helper;

public interface FieldHelper extends Helper {

	//Object getFieldValue(Object object,FieldName fieldName);
	
	String concatenate(Collection<String> names);
	String concatenate(String...names);
	
	Object getFieldValueSystemIdentifier(Object object);
	Object getFieldValueBusinessIdentifier(Object object);
	
	FieldHelper setFieldValueSystemIdentifier(Object object,Object value);
	FieldHelper setFieldValueBusinessIdentifier(Object object,Object value);
	
	FieldHelper copy(Object source,Object destination);
}
