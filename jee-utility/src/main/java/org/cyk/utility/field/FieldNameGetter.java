package org.cyk.utility.field;

import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;

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
