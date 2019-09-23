package org.cyk.utility.instance;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public abstract class AbstractInstanceBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements InstanceBuilder,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Object fieldsValuesObject;
	private Class<?> klass;
	private Object[] constructorParameters;
	
	@Override
	protected Object __execute__() throws Exception {
		Object instance = null;
		Object fieldsValuesObject = getFieldsValuesObject();
		if(fieldsValuesObject != null) {
			Class<?> aClass = getClazz();
			Object[] parameters = getConstructorParameters();
			instance = org.cyk.utility.__kernel__.klass.ClassHelper.instanciate(aClass,parameters);
			__execute__(fieldsValuesObject, instance);
		}
		return instance;
	}
	
	protected void __execute__(Object source,Object destination) {
		Properties properties = new Properties();
		properties.copyFrom(getProperties(), Properties.CONTEXT,Properties.REQUEST,Properties.FIELDS);
		__copy__(source, destination,properties);
	}
	
	protected void __copy__(Object source,Object destination,Properties properties) {
		__inject__(FieldHelper.class).copy(source, destination,properties);
	}
	
	@Override
	public Class<?> getClazz() {
		return klass;
	}

	@Override
	public InstanceBuilder setClazz(Class<?> klass) {
		this.klass = klass;
		return this;
	}
	
	@Override
	public Object[] getConstructorParameters() {
		return constructorParameters;
	}
	
	@Override
	public InstanceBuilder setConstructorParameters(Object[] constructorParameters) {
		this.constructorParameters = constructorParameters;
		return this;
	}
	
	@Override
	public Object getFieldsValuesObject() {
		return fieldsValuesObject;
	}
	
	@Override
	public InstanceBuilder setFieldsValuesObject(Object fieldsValuesObject) {
		this.fieldsValuesObject = fieldsValuesObject;
		return this;
	}
}
