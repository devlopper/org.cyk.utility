package org.cyk.utility.field;

import java.lang.reflect.Field;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface FieldInstance extends Objectable {

	Class<?> getClazz();
	FieldInstance setClazz(Class<?> clazz);
	
	String getPath();
	FieldInstance setPath(String path);
	
	Field getField();
	FieldInstance setField(Field field);
	
	Class<?> getType();
	FieldInstance setType(Class<?> type);
	
	Boolean getIsGeneratable();
	FieldInstance setIsGeneratable(Boolean isGeneratable);
}
