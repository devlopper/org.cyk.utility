package org.cyk.utility.request;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class RequestPropertyValueGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements RequestPropertyValueGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Object request;
	private RequestProperty property;
	
	@Override
	public Object getRequest() {
		return request;
	}

	@Override
	public RequestPropertyValueGetter setRequest(Object request) {
		this.request = request;
		return this;
	}

	@Override
	public RequestProperty getProperty() {
		return property;
	}

	@Override
	public RequestPropertyValueGetter setProperty(RequestProperty property) {
		this.property = property;
		return this;
	}

	
	
}
