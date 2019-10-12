package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.cyk.utility.__kernel__.annotation.Web;
import org.cyk.utility.client.controller.session.SessionHelper;

@ApplicationScoped @Web @Deprecated
public class SessionHelperImpl extends AbstractSessionHelperImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public Object getAttributeValue(Object name, Object request) {
		return __getAttributeValue__(name, request);
	}

	@Override
	public SessionHelper setAttributeValue(Object name, Object value, Object request) {
		__setAttributeValue__(name, value, request);
		return this;
	}
	
	public static Object __getAttributeValue__(Object name, Object request) {
		Object value = null;
		if(request!=null) {
			HttpSession session = ((HttpServletRequest)request).getSession();
			if(session!=null)
				value = session.getAttribute(__getAttributeNameAsString__(name));
		}
		return value;
	}

	public static void __setAttributeValue__(Object name, Object value, Object request) {
		if(request!=null) {
			HttpSession session = ((HttpServletRequest)request).getSession();
			if(session!=null)
				session.setAttribute(__getAttributeNameAsString__(name), value);
		}
	}

}
