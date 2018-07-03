package org.cyk.utility.field;

import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface FieldGetValue extends FunctionWithPropertiesAsInput<Object> {

	Object getObject();
	FieldGetValue setObject(Object object);
	
	Field getField();
	FieldGetValue setField(Field field);
	FieldGetValue setField(Class<?> aClass,Collection<String> names);
	FieldGetValue setField(Class<?> aClass,String...names);
	FieldGetValue setField(Collection<String> names);
	FieldGetValue setField(String...names);
	
	String getFieldName();
	FieldGetValue setFieldName(String fieldName);
	
	/**/
	
	FieldGetValue execute(Object object,Field field);
	FieldGetValue execute(Object object,String fieldName);
	
}
