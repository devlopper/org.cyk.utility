package org.cyk.utility.client.controller.component;

import java.lang.reflect.Field;

public interface InputOutput<T> extends VisibleComponent {

	Object getObject();
	InputOutput<T> setObject(Object object);
	
	Field getField();
	InputOutput<T> setField(Field field);
	
	T getValue();
	void setValue(T value);
	
}
