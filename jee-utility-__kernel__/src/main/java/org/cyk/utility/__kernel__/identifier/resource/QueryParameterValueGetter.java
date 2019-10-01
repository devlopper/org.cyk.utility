package org.cyk.utility.__kernel__.identifier.resource;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface QueryParameterValueGetter {

	default String get(String name,Object request) {
		if(StringHelper.isBlank(name))
			return null;
		//TODO : based on environment get request
		if(request == null)
			request = DependencyInjection.inject(HttpServletRequest.class);
		if(request == null)
			return null;
		if(request instanceof HttpServletRequest)
			return ((HttpServletRequest)request).getParameter(name);
		throw new RuntimeException("get query parameter named <<"+name+">> from request of type "+request.getClass()+" not yet implemented");
	}
	
	default String get(String name) {
		if(StringHelper.isBlank(name))
			return null;
		return get(name,null);
	}
	
	QueryParameterValueGetter INSTANCE = new QueryParameterValueGetter() {};
}
