package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class FieldInstanceImpl extends AbstractObject implements FieldInstance,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Field field;
	private Class<?> type;
	
	@Override
	public Field getField() {
		return field;
	}

	@Override
	public FieldInstance setField(Field field) {
		this.field = field;
		return this;
	}

	@Override
	public Class<?> getType() {
		return type;
	}

	@Override
	public FieldInstance setType(Class<?> type) {
		this.type = type;
		return this;
	}

}
