package org.cyk.utility.value;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Dependent @Deprecated
public class ValueConverterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements ValueConverter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object __execute__() throws Exception {
		Object value = getValue();
		if(value != null) {
			Class<?> clazz = getClazz();
			if(String.class.equals(clazz) && !(value instanceof String))
				value = value.toString();
			else if(ClassHelper.isInstanceOfNumber(clazz))
				value = NumberHelper.get(clazz, value);
		}
		return value;
	}
	
	@Override
	public ValueConverter execute(Object value, Class<?> aClass) {
		return (ValueConverter) setValue(value).setClazz(aClass).execute();
	}
	
	@Override
	public Object getValue() {
		return getProperties().getValue();
	}

	@Override
	public ValueConverter setValue(Object value) {
		getProperties().setValue(value);
		return this;
	}

	@Override
	public Class<?> getClazz() {
		return (Class<?>) getProperties().getClazz();
	}

	@Override
	public ValueConverter setClazz(Class<?> aClass) {
		getProperties().setClass(aClass);
		return this;
	}

}
