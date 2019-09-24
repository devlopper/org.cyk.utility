package org.cyk.utility.request;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.helper.Helper;

public interface RequestHelper extends Helper  {

	static Object getProperty(Object request,RequestProperty property) {
		if(property == null)
			return null;
		if(request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			switch(property) {
			case SCHEME: return httpServletRequest.getScheme();
			case HOST : return httpServletRequest.getServerName();
			case PORT : return httpServletRequest.getServerPort();
			case PATH : return httpServletRequest.getPathInfo();
			case QUERY : return httpServletRequest.getQueryString();
			default: return null;
			}
		}
		return null;
	}
	
	static String getPropertyScheme(Object request) {
		return (String) getProperty(request, RequestProperty.SCHEME);
	}
	
	static String getPropertyHost(Object request) {
		return (String) getProperty(request, RequestProperty.HOST);
	}
	
	static Integer getPropertyPort(Object request) {
		return (Integer) getProperty(request, RequestProperty.PORT);
	}
	
}
