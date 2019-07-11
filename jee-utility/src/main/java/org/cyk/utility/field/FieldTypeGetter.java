package org.cyk.utility.field;

import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.value.ValueUsageType;

public interface FieldTypeGetter extends FunctionWithPropertiesAsInput<FieldType> {

	FieldsGetter getFieldGetter();
	FieldsGetter getFieldGetter(Boolean injectIfNull);
	FieldTypeGetter setFieldGetter(FieldsGetter fieldGetter);
	
	Class<?> getClazz();
	FieldTypeGetter setClazz(Class<?> aClass);
	
	Field getField();
	FieldTypeGetter setField(Field field);
	FieldTypeGetter setField(Class<?> aClass,Collection<String> names);
	FieldTypeGetter setField(Class<?> aClass,String...names);
	FieldTypeGetter setField(Collection<String> names);
	FieldTypeGetter setField(String...names);
	FieldTypeGetter setField(FieldName fieldName,ValueUsageType valueUsageType);
	
	String getFieldName();
	FieldTypeGetter setFieldName(String fieldName);
	
	/**/
	
	FieldTypeGetter execute(Field field);
	FieldTypeGetter execute(Class<?> aClass,String fieldName);
	FieldTypeGetter execute(Class<?> aClass,FieldName fieldName,ValueUsageType valueUsageType);
}
