package org.cyk.utility.__kernel__.field;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface FieldInstance extends Objectable {

	Class<?> getClazz();
	FieldInstance setClazz(Class<?> clazz);
	
	String getPath();
	FieldInstance setPath(String path);
	
	Field getField();
	FieldInstance setField(Field field);
	
	Type getType();
	FieldInstance setType(Type type);
	
	Boolean getIsGeneratable();
	FieldInstance setIsGeneratable(Boolean isGeneratable);
	
	Map<Action,Class<?>> getActionsClassesMap();
	Map<Action,Class<?>> getActionsClassesMap(Boolean instantiateIfNull);
	FieldInstance setActionsClassesMap(Map<Action,Class<?>> actionsClassesMap);
	FieldInstance setActionClass(Action action,Class<?> klass);
	Class<?> getActionClass(Action action);
	
	public static enum Action{
		BUILD_INPUT,BUILD_OUTPUT,INPUT,OUTPUT
	}
}
