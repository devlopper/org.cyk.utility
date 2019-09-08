package org.cyk.utility.value;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.field.FieldInstance;

public interface Value extends Objectable {

	String getName();
	Value setName(String name);
	
	Object getObject();
	Value setObject(Object fieldObject);
	
	FieldInstance getFieldInstance();
	Value setFieldInstance(FieldInstance fieldInstance);
	
	Object get();
	Value set(Object value);
	
}
