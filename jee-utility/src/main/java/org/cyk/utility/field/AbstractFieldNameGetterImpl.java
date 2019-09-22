package org.cyk.utility.field;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Dependent @Deprecated
public abstract class AbstractFieldNameGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<String> implements FieldNameGetter, Serializable {
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
		String value = __inject__(FieldNameValueUsageMap.class).get(aClass, fieldName, valueUsageType);
		if(value == null)
			value = fieldName.getByValueUsageType(valueUsageType);
		return value;
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
