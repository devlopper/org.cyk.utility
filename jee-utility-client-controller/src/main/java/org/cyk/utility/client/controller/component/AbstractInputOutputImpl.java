package org.cyk.utility.client.controller.component;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.field.FieldValueSetter;

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
	
	@Override
	public Object getPropertyValue() {
		return getProperty(Properties.VALUE);
	}
	
	@Override
	public InputOutput<T> setPropertyValue(Object value) {
		setProperty(Properties.VALUE, value);
		return this;
	}
	
	@Override
	public InputOutput<T> setValueFromFieldValue() {
		Object object = getObject();
		Field field = getField();
		if(object!=null && field!=null)
			setValue(__getValueToSetValueFromFieldValue__(object,field));
		return this;
	}
	
	protected T __getValueToSetValueFromFieldValue__(Object object,Field field) {
		return (T) ____getValueToSetValueFromFieldValue____(object, field);
	}
	
	protected Object ____getValueToSetValueFromFieldValue____(Object object,Field field) {
		return __inject__(FieldValueGetter.class).execute(object, field).getOutput();
	}
	
	@Override
	public InputOutput<T> setFieldValueFromValue() {
		Object object = getObject();
		Field field = getField();
		if(object!=null && field!=null) {
			//System.out.println("AbstractInputOutputImpl.setFieldValueFromValue() "+object+" : "+field+" : "+__getValueToSetFieldValueFromValue__());
			__inject__(FieldValueSetter.class).execute(object, field, __getValueToSetFieldValueFromValue__());
		}
		return this;
	}
	
	protected T __getValueToSetFieldValueFromValue__() {
		return getValue();
	}
}
