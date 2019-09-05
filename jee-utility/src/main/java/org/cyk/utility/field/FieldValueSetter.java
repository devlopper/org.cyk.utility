package org.cyk.utility.field;

import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;
import org.cyk.utility.value.ValueUsageType;

@Deprecated
public interface FieldValueSetter extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	Object getObject();
	FieldValueSetter setObject(Object object);
	
	Object getValue();
	FieldValueSetter setValue(Object value);
	
	Field getField();
	FieldValueSetter setField(Field field);
	FieldValueSetter setField(Class<?> aClass,Collection<String> names);
	FieldValueSetter setField(Class<?> aClass,String...names);
	FieldValueSetter setField(Collection<String> names);
	FieldValueSetter setField(String...names);
	FieldValueSetter setField(FieldName fieldName,ValueUsageType valueUsageType);
	
	String getFieldName();
	FieldValueSetter setFieldName(String fieldName);
	
	/**/
	
	FieldValueSetter execute(Object object,Field field,Object value);
	FieldValueSetter execute(Object object,String fieldName,Object value);
	FieldValueSetter execute(Object object,FieldName fieldName,ValueUsageType valueUsageType,Object value);
}
