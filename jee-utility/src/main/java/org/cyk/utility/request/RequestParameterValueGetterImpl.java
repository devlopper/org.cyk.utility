package org.cyk.utility.request;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Dependent @Deprecated
public class RequestParameterValueGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements RequestParameterValueGetter,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Object request,parameterName;
	
	/*@Override
	protected Object __execute__() throws Exception {
		Object value = null;
		Object request = getRequest();
		if(request == null)
			request = __inject__(RequestGetter.class).execute().getOutput();
		return value;
	}
	*/
	@Override
	public Object getRequest() {
		return request;
	}

	@Override
	public RequestParameterValueGetter setRequest(Object request) {
		this.request = request;
		return this;
	}

	@Override
	public Object getParameterName() {
		return parameterName;
	}

	@Override
	public RequestParameterValueGetter setParameterName(Object parameterName) {
		this.parameterName = parameterName;
		return this;
	}

}
