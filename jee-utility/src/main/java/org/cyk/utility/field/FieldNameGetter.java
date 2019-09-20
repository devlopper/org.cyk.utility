package org.cyk.utility.field;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.value.ValueUsageType;

@Deprecated
public interface FieldNameGetter extends FunctionWithPropertiesAsInput<String> {

	Class<?> getClazz();
	FieldNameGetter setClazz(Class<?> aClass);
	
	FieldName getFieldName();
	FieldNameGetter setFieldName(FieldName fieldName);
	
	ValueUsageType getValueUsageType();
	FieldNameGetter setValueUsageType(ValueUsageType valueUsageType);
	
	/**/
	
	FieldNameGetter execute(Class<?> aClass,FieldName fieldName,ValueUsageType valueUsageType);
	FieldNameGetter execute(Class<?> aClass,FieldName fieldName);
}
