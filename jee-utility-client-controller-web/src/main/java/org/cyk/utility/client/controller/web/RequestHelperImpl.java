package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.annotation.Web;
import org.cyk.utility.request.AbstractRequestHelperImpl;
import org.cyk.utility.request.RequestProperty;

@ApplicationScoped @Web
public class RequestHelperImpl extends AbstractRequestHelperImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Object getProperty(Object request,RequestProperty property) {
		if(property == null)
			return null;
		if(request instanceof HttpServletRequest) {
			switch(property) {
			case SCHEME: return ((HttpServletRequest)request).getScheme();
			case HOST : return ((HttpServletRequest)request).getServerName();
			case PORT : return ((HttpServletRequest)request).getServerPort();
			case PATH : return ((HttpServletRequest)request).getPathInfo();
			case QUERY : return ((HttpServletRequest)request).getQueryString();
			default: return null;
			}
		}
		return null;
	}

}