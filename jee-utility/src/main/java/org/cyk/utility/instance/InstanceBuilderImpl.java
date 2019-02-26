package org.cyk.utility.instance;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class InstanceBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements InstanceBuilder,Serializable {
	private static final long serialVersionUID = 1L;
/*
	@Override
	protected INSTANCE __execute__() throws Exception {
		INSTANCE instance = null;
		Object fieldsValuesObject = getFieldsValuesObject();
		if(fieldsValuesObject != null) {
			Class<?> aClass = getClazz();
			Object[] parameters = getConstructorParameters();
			instance = (INSTANCE) __injectClassHelper__().instanciate(aClass,parameters);
			__injectFieldHelper__().copy(fieldsValuesObject, instance);
		}
		return instance;
	}
*/
	@Override
	public Class<?> getClazz() {
		return (Class<?>) getProperties().getClazz();
	}

	@Override
	public InstanceBuilder setClazz(Class<?> aClass) {
		getProperties().setClass(aClass);
		return this;
	}
	
	@Override
	public Object[] getConstructorParameters() {
		return (Object[]) getProperties().getParameters();
	}
	
	@Override
	public InstanceBuilder setConstructorParameters(Object[] parameters) {
		getProperties().setParameters(parameters);
		return this;
	}
	
	@Override
	public Object getFieldsValuesObject() {
		return getProperties().getCopy();
	}
	
	@Override
	public InstanceBuilder setFieldsValuesObject(Object copy) {
		getProperties().setCopy(copy);
		return this;
	}
}
