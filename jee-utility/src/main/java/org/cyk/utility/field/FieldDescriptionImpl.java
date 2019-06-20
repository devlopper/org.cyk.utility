package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

@Dependent
public class FieldDescriptionImpl extends AbstractObject implements FieldDescription,Serializable {
	private static final long serialVersionUID = 1L;

	private Field field;
	private String name;
	private Boolean isNullable,isEditable;
	
	@Override
	public Field getField() {
		return field;
	}

	@Override
	public FieldDescription setField(Field field) {
		this.field = field;
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public FieldDescription setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public Boolean getIsNullable() {
		return isNullable;
	}

	@Override
	public FieldDescription setIsNullable(Boolean isNullable) {
		this.isNullable = isNullable;
		return this;
	}
	
	@Override
	public Boolean getIsEditable() {
		return isEditable;
	}
	
	@Override
	public FieldDescription setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
		return this;
	}

}
