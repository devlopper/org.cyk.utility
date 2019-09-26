package org.cyk.utility.__kernel__.value;

import org.cyk.utility.__kernel__.field.FieldInstance;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;

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
