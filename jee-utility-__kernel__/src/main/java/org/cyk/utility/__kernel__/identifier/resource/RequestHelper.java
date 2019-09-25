package org.cyk.utility.__kernel__.identifier.resource;

import javax.servlet.http.HttpServletRequest;

public interface RequestHelper  {

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
	
}
