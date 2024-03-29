package org.cyk.utility.__kernel__.identifier.resource;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface RequestHelper  {

	static Object get() {
		//TODO must be based on environment features
		return DependencyInjection.inject(HttpServletRequest.class);
	}
	
	static Object getProperty(Object request,RequestProperty property) {
		if(request == null || property == null)
			return null;
		Object value = null;
		if(request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			if(RequestProperty.SCHEME.equals(property))
				value = httpServletRequest.getScheme();
			else if(RequestProperty.HOST.equals(property))
				value = httpServletRequest.getServerName();
			else if(RequestProperty.PORT.equals(property))
				value = httpServletRequest.getServerPort();
			else if(RequestProperty.PATH.equals(property))
				value = httpServletRequest.getPathInfo();
			else if(RequestProperty.QUERY.equals(property))
				value = httpServletRequest.getQueryString();
		}
		if(value == null)
			throw new RuntimeException("cannot get request property "+property+" from request "+request);
		return value;
	}
	
	static String getPropertyScheme(Object request) {
		if(request == null)
			return null;
		return (String) getProperty(request, RequestProperty.SCHEME);
	}
	
	static String getPropertyHost(Object request) {
		if(request == null)
			return null;
		return (String) getProperty(request, RequestProperty.HOST);
	}
	
	static Integer getPropertyPort(Object request) {
		if(request == null)
			return null;
		return (Integer) getProperty(request, RequestProperty.PORT);
	}
	
	static Object getParameter(String name,Object request) {
		if(request == null || StringHelper.isBlank(name))
			return null;
		if(request instanceof HttpServletRequest)
			return ((HttpServletRequest)request).getParameter(name);
		throw new RuntimeException("get parameter from request of type <<"+request.getClass()+">> not yet implemented");
	}
	
	static Object getParameter(String name) {
		return getParameter(name, get());
	}
}
