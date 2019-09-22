package org.cyk.utility.instance;

import java.util.Collection;

import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface InstanceGetter extends FunctionWithPropertiesAsInput<Collection<Object>> {

	InstanceGetter setClazz(Class<?> aClass);
	Class<?> getClazz();
	
	InstanceGetter setFieldName(FieldName fieldName);
	FieldName getFieldName();
	
	InstanceGetter setValueUsageType(ValueUsageType valueUsageType);
	ValueUsageType getValueUsageType();
	
	InstanceGetter setValue(Object value);
	Object getValue();
}
