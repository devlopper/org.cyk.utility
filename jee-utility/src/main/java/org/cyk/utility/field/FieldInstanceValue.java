package org.cyk.utility.field;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface FieldInstanceValue extends Objectable {

	FieldInstance getFieldInstance();
	FieldInstanceValue setFieldInstance(FieldInstance fieldInstance);
	
	Object getValue();
	FieldInstanceValue setValue(Object value);
	
	/**/
	
	String PROPERTY_FIELD_INSTANCE = "fieldInstance";
	String PROPERTY_VALUE = "value";
}
