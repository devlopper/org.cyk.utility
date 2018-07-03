package org.cyk.utility.field;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.value.ValueUsageType;

public interface FieldGetName extends FunctionWithPropertiesAsInput<String> {

	Class<?> getClazz();
	FieldGetName setClazz(Class<?> aClass);
	
	FieldName getFieldName();
	FieldGetName setFieldName(FieldName fieldName);
	
	ValueUsageType getValueUsageType();
	FieldGetName setValueUsageType(ValueUsageType valueUsageType);
	
	/**/
	
	FieldGetName execute(Class<?> aClass,FieldName fieldName,ValueUsageType valueUsageType);
	FieldGetName execute(Class<?> aClass,FieldName fieldName);
}
