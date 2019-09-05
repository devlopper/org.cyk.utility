package org.cyk.utility.field;

import org.cyk.utility.__kernel__.object.dynamic.Singleton;
import org.cyk.utility.value.ValueUsageType;

@Deprecated
public interface FieldNameValueUsageMap extends Singleton {

	FieldNameValueUsageMap set(Class<?> aClass,FieldName fieldName,ValueUsageType valueUsageType,String value);
	String get(Class<?> aClass,FieldName fieldName,ValueUsageType valueUsageType);
}
