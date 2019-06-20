package org.cyk.utility.field;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

@Dependent
public class FieldInstanceValueImpl extends AbstractObject implements FieldInstanceValue,Serializable {
	private static final long serialVersionUID = 1L;
	
	private FieldInstance fieldInstance;
	private Object value;
	
	@Override
	public FieldInstance getFieldInstance() {
		return fieldInstance;
	}

	@Override
	public FieldInstanceValue setFieldInstance(FieldInstance fieldInstance) {
		this.fieldInstance = fieldInstance;
		return this;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public FieldInstanceValue setValue(Object value) {
		this.value = value;
		return this;
	}

}
