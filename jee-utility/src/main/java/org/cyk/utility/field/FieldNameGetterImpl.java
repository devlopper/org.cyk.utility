package org.cyk.utility.field;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.value.ValueUsageType;

public class FieldNameGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<String> implements FieldNameGetter, Serializable {
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
	public FieldNameGetter execute(Class<?> aClass, FieldName fieldName, ValueUsageType valueUsageType) {
		return (FieldNameGetter) setClazz(aClass).setFieldName(fieldName).setValueUsageType(valueUsageType).execute();
	}
	
	@Override
	public FieldNameGetter execute(Class<?> aClass, FieldName fieldName) {
		return execute(aClass, fieldName, ValueUsageType.BUSINESS);
	}

	@Override
	public Class<?> getClazz() {
		return (Class<?>) getProperties().getClazz();
	}

	@Override
	public FieldNameGetter setClazz(Class<?> aClass) {
		getProperties().setClass(aClass);
		return this;
	}

	@Override
	public FieldName getFieldName() {
		return (FieldName) getProperties().getFieldName();
	}

	@Override
	public FieldNameGetter setFieldName(FieldName fieldName) {
		getProperties().setFieldName(fieldName);
		return this;
	}

	@Override
	public ValueUsageType getValueUsageType() {
		return (ValueUsageType) getProperties().getValueUsageType();
	}

	@Override
	public FieldNameGetter setValueUsageType(ValueUsageType valueUsageType) {
		getProperties().setValueUsageType(valueUsageType);
		return this;
	}

}
