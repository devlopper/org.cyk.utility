package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class FieldInstanceImpl extends AbstractObject implements FieldInstance,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Class<?> clazz;
	private String path;
	private Field field;
	private Class<?> type;
	private Boolean isGeneratable;
	
	@Override
	public Class<?> getClazz() {
		return clazz;
	}

	@Override
	public FieldInstance setClazz(Class<?> clazz) {
		this.clazz = clazz;
		return this;
	}
	
	@Override
	public String getPath() {
		return path;
	}

	@Override
	public FieldInstance setPath(String path) {
		this.path = path;
		return this;
	}
	
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

	@Override
	public Boolean getIsGeneratable() {
		return isGeneratable;
	}
	
	@Override
	public FieldInstance setIsGeneratable(Boolean isValueGeneratable) {
		this.isGeneratable = isValueGeneratable;
		return this;
	}
}
