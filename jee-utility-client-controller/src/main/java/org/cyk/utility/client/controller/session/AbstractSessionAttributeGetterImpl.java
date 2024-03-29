package org.cyk.utility.client.controller.session;

import java.io.Serializable;

import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Deprecated
public abstract class AbstractSessionAttributeGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements SessionAttributeGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Object request;
	private Object attribute;
	
	@Override
	protected Object __execute__() throws Exception {
		Object attribute = ValueHelper.returnOrThrowIfBlank("user session attribute", getAttribute());
		if(attribute instanceof SessionAttributeEnumeration)
			attribute = ((SessionAttributeEnumeration)attribute).name();
		
		Object request = ValueHelper.returnOrThrowIfBlank("request to get attribute <<"+attribute+">>", getRequest());
		
		return __execute__(attribute,request);
	}
	
	protected abstract Object __execute__(Object attribute,Object request);
	
	@Override
	public Object getRequest() {
		return request;
	}
	
	@Override
	public SessionAttributeGetter setRequest(Object request) {
		this.request = request;
		return this;
	}
	
	@Override
	public Object getAttribute() {
		return attribute;
	}

	@Override
	public SessionAttributeGetter setAttribute(Object attribute) {
		this.attribute = attribute;
		return this;
	}
	
}
