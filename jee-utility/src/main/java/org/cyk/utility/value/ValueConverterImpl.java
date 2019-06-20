package org.cyk.utility.value;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.number.NumberHelper;

@Dependent
public class ValueConverterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements ValueConverter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object __execute__() throws Exception {
		Object value = getValue();
		if(value != null) {
			Class<?> clazz = getClazz();
			if(String.class.equals(clazz) && !(value instanceof String))
				value = value.toString();
			else if(__injectClassHelper__().isInstanceOfNumber(clazz))
				value = __inject__(NumberHelper.class).get(clazz, value);
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
