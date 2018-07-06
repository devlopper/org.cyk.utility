package org.cyk.utility.field;

import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface FieldValueGetter extends FunctionWithPropertiesAsInput<Object> {

	Object getObject();
	FieldValueGetter setObject(Object object);
	
	Field getField();
	FieldValueGetter setField(Field field);
	FieldValueGetter setField(Class<?> aClass,Collection<String> names);
	FieldValueGetter setField(Class<?> aClass,String...names);
	FieldValueGetter setField(Collection<String> names);
	FieldValueGetter setField(String...names);
	
	String getFieldName();
	FieldValueGetter setFieldName(String fieldName);
	
	/**/
	
	FieldValueGetter execute(Object object,Field field);
	FieldValueGetter execute(Object object,String fieldName);
	
}
