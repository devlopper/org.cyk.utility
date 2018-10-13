package org.cyk.utility.client.controller.component;

import java.io.Serializable;
import java.lang.reflect.Field;

public abstract class AbstractInputOutputImpl<T> extends AbstractVisibleComponentImpl implements InputOutput<T>,Serializable {
	private static final long serialVersionUID = 1L;

	protected Object object;
	protected Field field;
	protected T value;
	
	@Override
	public Object getObject() {
		return object;
	}
	
	@Override
	public InputOutput<T> setObject(Object object) {
		this.object = object;
		return this;
	}
	
	@Override
	public Field getField() {
		return field;
	}
	
	@Override
	public InputOutput<T> setField(Field field) {
		this.field = field;
		return this;
	}
	
	@Override
	public T getValue() {
		return value;
	}
	
	@Override
	public void setValue(T value) {
		this.value = value;
	}
}
