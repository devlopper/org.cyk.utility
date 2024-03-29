package org.cyk.utility.request;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.identifier.resource.RequestProperty;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Dependent
public class RequestPropertyValueGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements RequestPropertyValueGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Object request;
	private RequestProperty property;
	
	@Override
	protected Object __execute__() throws Exception {
		Object request = ValueHelper.returnOrThrowIfBlank("request", getRequest());
		RequestProperty property = ValueHelper.returnOrThrowIfBlank("request property", getProperty());
		Object value = null;
		if(request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			if(RequestProperty.SCHEME.equals(property))
				value = httpServletRequest.getScheme();
			else if(RequestProperty.HOST.equals(property))
				value = httpServletRequest.getServerName();
			else if(RequestProperty.PORT.equals(property))
				value = httpServletRequest.getServerPort();
			else if(RequestProperty.CONTEXT.equals(property))
				value = httpServletRequest.getContextPath();
			/*else if(RequestProperty.PATH.equals(property))
				value = httpServletRequest.getPathInfo();
			else if(RequestProperty.QUERY.equals(property))
				value = httpServletRequest.getQueryString();
			*/
			//else
			//	ThrowableHelper.throwNotYetImplemented("get request "+property+" property");
		}
		return value;
	}
	
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
