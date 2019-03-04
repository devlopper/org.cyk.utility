package org.cyk.utility.field;

import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.helper.Helper;

public interface FieldHelper extends Helper {

	//Object getFieldValue(Object object,FieldName fieldName);
	
	String concatenate(Collection<String> names);
	String concatenate(String...names);
	
	Object getFieldValueSystemIdentifier(Object object);
	Object getFieldValueBusinessIdentifier(Object object);
	Object getFieldValueSystemOrBusinessIdentifier(Object object);
	
	FieldHelper setFieldValueSystemIdentifier(Object object,Object value);
	FieldHelper setFieldValueBusinessIdentifier(Object object,Object value);
	
	FieldHelper copy(Object source,Object destination,Properties properties);
	FieldHelper copy(Object source,Object destination);
	
	<TYPE> Class<TYPE> getParameterAt(Field field, Integer index, Class<TYPE> typeClass);
	
	Field getField(Class<?> aClass,Collection<String> fieldNames);
	Field getField(Class<?> aClass,String...fieldNames);
	
}
