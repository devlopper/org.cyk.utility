package org.cyk.utility.instance;

import java.io.Serializable;

import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.FieldNameGetter;
import org.cyk.utility.field.FieldValueCopy;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.value.ValueUsageType;

public class InstanceBuilderImpl<INSTANCE> extends AbstractFunctionWithPropertiesAsInputImpl<INSTANCE> implements InstanceBuilder<INSTANCE>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected INSTANCE __execute__() throws Exception {
		Class<?> aClass = getClazz();
		Object[] parameters = getParameters();
		INSTANCE instance = (INSTANCE) __injectClassHelper__().instanciate(aClass,parameters);
		Object copy = getCopy();
		if(copy != null) {
			for(FieldName indexFieldName : new FieldName[] {FieldName.IDENTIFIER})
				for(ValueUsageType indexValueUsageType : new ValueUsageType[] {ValueUsageType.SYSTEM,ValueUsageType.BUSINESS}) {
					String fieldName = __inject__(FieldNameGetter.class).execute(instance.getClass(), indexFieldName, indexValueUsageType).getOutput();
					__inject__(FieldValueCopy.class).execute(copy, instance, fieldName);
				}		
		}
		return instance;
	}

	@Override
	public Class<INSTANCE> getClazz() {
		return (Class<INSTANCE>) getProperties().getClazz();
	}

	@Override
	public InstanceBuilder<INSTANCE> setClazz(Class<?> aClass) {
		getProperties().setClass(aClass);
		return this;
	}
	
	@Override
	public Object[] getParameters() {
		return (Object[]) getProperties().getParameters();
	}
	
	@Override
	public InstanceBuilder<INSTANCE> setParameters(Object[] parameters) {
		getProperties().setParameters(parameters);
		return this;
	}
	
	@Override
	public Object getCopy() {
		return getProperties().getCopy();
	}
	
	@Override
	public InstanceBuilder<INSTANCE> setCopy(Object copy) {
		getProperties().setCopy(copy);
		return this;
	}
}
