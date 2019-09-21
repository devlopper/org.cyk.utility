package org.cyk.utility.field;

import java.lang.reflect.Field;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.map.MapInstanceIntegerToClass;

@Deprecated
public interface FieldType extends Objectable {

	Field getField();
	FieldType setField(Field field);
	
	Class<?> getType();
	FieldType setType(Class<?> type);
	
	MapInstanceIntegerToClass getParameterizedClasses();
	MapInstanceIntegerToClass getParameterizedClasses(Boolean injectIfNull);
	FieldType setParameterizedClasses(MapInstanceIntegerToClass parameterizedClasses);
}
