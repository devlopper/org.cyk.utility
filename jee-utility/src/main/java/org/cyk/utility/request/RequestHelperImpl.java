package org.cyk.utility.request;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpServletRequest;

@ApplicationScoped
public class RequestHelperImpl extends AbstractRequestHelperImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Object getProperty(Object request,RequestProperty property) {
		return null;
	}

	public static Object __getProperty__(Object request,RequestProperty property) {
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
	
	public static String __getPropertyScheme__(Object request) {
		return (String) __getProperty__(request, RequestProperty.SCHEME);
	}
	
	public static String __getPropertyHost__(Object request) {
		return (String) __getProperty__(request, RequestProperty.HOST);
	}
	
	public static Integer __getPropertyPort__(Object request) {
		return (Integer) __getProperty__(request, RequestProperty.PORT);
	}
}
