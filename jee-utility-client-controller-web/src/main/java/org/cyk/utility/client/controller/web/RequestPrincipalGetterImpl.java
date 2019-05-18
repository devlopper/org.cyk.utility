package org.cyk.utility.client.controller.web;

import java.io.Serializable;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.annotation.Web;
import org.cyk.utility.request.AbstractRequestPrincipalGetterImpl;

@Web
public class RequestPrincipalGetterImpl extends AbstractRequestPrincipalGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Principal __execute__(Object request) {
		Principal value = null;
		if(request!=null)
			value = ((HttpServletRequest)request).getUserPrincipal();
		return value;
	}
	
}