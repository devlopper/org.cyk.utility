package org.cyk.utility.field;

import java.lang.reflect.Field;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface FieldDescription extends Objectable {

	Field getField();
	FieldDescription setField(Field field);
	
	String getName();
	FieldDescription setName(String name);
	
	//String getLabelInternalizationStringIdentifier();
	//FieldDescription setLabelInternalizationStringIdentifier();
	
	Boolean getIsNullable();
	FieldDescription setIsNullable(Boolean isNullable);
	
	Boolean getIsEditable();
	FieldDescription setIsEditable(Boolean isEditable);
}