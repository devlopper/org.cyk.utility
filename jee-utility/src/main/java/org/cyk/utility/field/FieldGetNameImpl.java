package org.cyk.utility.field;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.value.ValueUsageType;

public class FieldGetNameImpl extends AbstractFunctionWithPropertiesAsInputImpl<String> implements FieldGetName, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __execute__() {
		String name = null;
		FieldName fieldName = getFieldName();
		if(fieldName != null){
			Class<?> aClass = getClazz();
			ValueUsageType valueUsageType = getValueUsageType();
			name = __execute__(aClass, fieldName, valueUsageType);
		}
		return name;
	}
	
	protected String __execute__(Class<?> aClass,FieldName fieldName,ValueUsageType valueUsageType) {
		return fieldName.getByValueUsageType(valueUsageType);
	}
	
	@Override
	public FieldGetName execute(Class<?> aClass, FieldName fieldName, ValueUsageType valueUsageType) {
		return (FieldGetName) setClazz(aClass).setFieldName(fieldName).setValueUsageType(valueUsageType).execute();
	}
	
	@Override
	public FieldGetName execute(Class<?> aClass, FieldName fieldName) {
		return execute(aClass, fieldName, ValueUsageType.BUSINESS);
	}

	@Override
	public Class<?> getClazz() {
		return (Class<?>) getProperties().getClazz();
	}

	@Override
	public FieldGetName setClazz(Class<?> aClass) {
		getProperties().setClass(aClass);
		return this;
	}

	@Override
	public FieldName getFieldName() {
		return (FieldName) getProperties().getFieldName();
	}

	@Override
	public FieldGetName setFieldName(FieldName fieldName) {
		getProperties().setFieldName(fieldName);
		return this;
	}

	@Override
	public ValueUsageType getValueUsageType() {
		return (ValueUsageType) getProperties().getValueUsageType();
	}

	@Override
	public FieldGetName setValueUsageType(ValueUsageType valueUsageType) {
		getProperties().setValueUsageType(valueUsageType);
		return this;
	}

}
