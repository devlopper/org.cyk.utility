package org.cyk.utility.request;

import java.io.Serializable;
import java.security.Principal;

import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public abstract class AbstractRequestPrincipalGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Principal> implements RequestPrincipalGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Object request;

	@Override
	protected Principal __execute__() throws Exception {
		Object request = ValueHelper.returnOrThrowIfBlank("request to get principal", getRequest());
		return __execute__(request);
	}
	
	protected abstract Principal __execute__(Object request);
	
	@Override
	public Object getRequest() {
		return request;
	}
	
	@Override
	public RequestPrincipalGetter setRequest(Object request) {
		this.request = request;
		return this;
	}
	
}
