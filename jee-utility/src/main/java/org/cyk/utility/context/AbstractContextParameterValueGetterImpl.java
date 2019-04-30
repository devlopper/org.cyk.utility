package org.cyk.utility.context;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.system.SystemHelper;

public abstract class AbstractContextParameterValueGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements ContextParameterValueGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Object context,nullValue;
	private String name;
	
	@Override
	protected Object __execute__() throws Exception {
		Object context = __injectValueHelper__().returnOrThrowIfBlank("context", getContext());
		String name = getName();
		Object value = __execute__(context,name);
		if(value == null)
			value = getNullValue();
		return value;
	}
	
	protected Object __execute__(Object context,String name) throws Exception {
		return __inject__(SystemHelper.class).getProperty(name, Boolean.TRUE);
	}
	
	@Override
	public Object getNullValue() {
		return nullValue;
	}
	
	@Override
	public ContextParameterValueGetter setNullValue(Object nullValue) {
		this.nullValue = nullValue;
		return this;
	}
	
	@Override
	public Object getContext() {
		return context;
	}
	
	@Override
	public ContextParameterValueGetter setContext(Object context) {
		this.context = context;
		return this;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public ContextParameterValueGetter setName(String name) {
		this.name = name;
		return this;
	}
}