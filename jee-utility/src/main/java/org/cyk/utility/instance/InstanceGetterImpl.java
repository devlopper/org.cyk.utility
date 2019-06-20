package org.cyk.utility.instance;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.field.FieldName;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.value.ValueUsageType;

@Dependent
public class InstanceGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Collection<Object>> implements InstanceGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public InstanceGetter setClazz(Class<?> aClass) {
		getProperties().setClass(aClass);
		return this;
	}

	@Override
	public Class<?> getClazz() {
		return (Class<?>) getProperties().getClazz();
	}
	
	@Override
	public InstanceGetter setFieldName(FieldName fieldName) {
		getProperties().setFieldName(fieldName);
		return this;
	}

	@Override
	public FieldName getFieldName() {
		return (FieldName) getProperties().getFieldName();
	}

	@Override
	public InstanceGetter setValueUsageType(ValueUsageType valueUsageType) {
		getProperties().setValueUsageType(valueUsageType);
		return this;
	}

	@Override
	public ValueUsageType getValueUsageType() {
		return (ValueUsageType) getProperties().getValueUsageType();
	}

	
	@Override
	public InstanceGetter setValue(Object value) {
		getProperties().setValue(value);
		return this;
	}

	@Override
	public Object getValue() {
		return getProperties().getValue();
	}

	

}
