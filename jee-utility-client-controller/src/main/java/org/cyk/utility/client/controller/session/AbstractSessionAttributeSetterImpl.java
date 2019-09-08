package org.cyk.utility.client.controller.session;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;

@Deprecated
public abstract class AbstractSessionAttributeSetterImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements SessionAttributeSetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Object request;
	private Object attribute;
	private Object value;

	@Override
	protected void ____execute____() throws Exception {
		Object attribute = __injectValueHelper__().returnOrThrowIfBlank("user session attribute", getAttribute());
		if(attribute instanceof SessionAttributeEnumeration)
			attribute = ((SessionAttributeEnumeration)attribute).name();
		Object value = getValue();
		Object request = getRequest();
		__execute__(attribute,value,request);
	}
	
	protected abstract void __execute__(Object attribute,Object value,Object request);
	
	@Override
	public Object getRequest() {
		return request;
	}
	
	@Override
	public SessionAttributeSetter setRequest(Object request) {
		this.request = request;
		return this;
	}
	
	@Override
	public Object getAttribute() {
		return attribute;
	}

	@Override
	public SessionAttributeSetter setAttribute(Object attribute) {
		this.attribute = attribute;
		return this;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public SessionAttributeSetter setValue(Object value) {
		this.value = value;
		return this;
	}
	
}
