package org.cyk.utility.client.controller.context;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class ContextPropertyValueGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements ContextPropertyValueGetter,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Object context;
	private ContextPropertyName propertyName;
	
	@Override
	public Object getContext() {
		return context;
	}

	@Override
	public ContextPropertyValueGetter setContext(Object context) {
		this.context = context;
		return this;
	}

	@Override
	public ContextPropertyName getPropertyName() {
		return propertyName;
	}

	@Override
	public ContextPropertyValueGetter setPropertyName(ContextPropertyName propertyName) {
		this.propertyName = propertyName;
		return this;
	}

}
