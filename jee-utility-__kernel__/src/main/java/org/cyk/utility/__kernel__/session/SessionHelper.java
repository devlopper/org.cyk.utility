package org.cyk.utility.__kernel__.session;

import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.identifier.resource.RequestHelper;
import org.cyk.utility.__kernel__.security.SecurityHelper;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;

public interface SessionHelper {

	static Object get(Object request) {
		if(request == null)
			return null;
		if(request instanceof HttpServletRequest)
			return ((HttpServletRequest)request).getSession();
		throw new RuntimeException("get session from request of type "+request.getClass()+" not yet implemented");
	}
	
	static Object get() {
		return get(RequestHelper.get());
	}
	
	static String buildAttributeName(Object object) {
		if(object == null)
			return null;
		if(object instanceof Class<?>)
			return ((Class<?>)object).getName();
		return object.toString();
	}
	
	static Object getAttributeValue(Object name,Object session) {
		if(name == null || session == null)
			return null;
		if(session instanceof HttpSession)
			return ((HttpSession)session).getAttribute(buildAttributeName(name));
		throw new RuntimeException("get session attribute value from type "+session.getClass()+" not yet implemented");
	}
	
	static Object getAttributeValue(Object name) {
		return getAttributeValue(name, get());
	}
	
	static void setAttributeValue(Object name,Object value,Object session) {
		if(name == null || session == null)
			return;
		if(session instanceof HttpSession)
			((HttpSession)session).setAttribute(buildAttributeName(name),value);
		else
			throw new RuntimeException("set session attribute value from type "+session.getClass()+" not yet implemented");
	}
	
	static void setAttributeValue(Object name,Object value) {
		setAttributeValue(name, value, get());
	}
	
	static Object getAttributeValueFromRequest(Object name,Object request) {
		if(name == null || request == null)
			return null;
		Object session = get(request);
		if(session == null)
			return null;
		return getAttributeValue(name, session);
	}
	
	static void setAttributeValueFromRequest(Object name,Object value,Object request) {
		if(name == null || request == null)
			return;
		Object session = get(request);
		if(session == null)
			return;
		setAttributeValue(name,value, session);
	}
	
	static Session getAttributeSession(Object session,Boolean createIfNull) {
		if(session == null)
			return null;
		Session value = (Session) getAttributeValue(Session.class, session);
		if(value == null && Boolean.TRUE.equals(createIfNull)) {
			value = new Session();
			setAttributeSession(value, session);
		}
		return value;
	}
	
	static Session getAttributeSession(Object session) {
		if(session == null)
			return null;
		return getAttributeSession(session, null);
	}
	
	static Session getAttributeSession(Boolean createIfNull) {
		return getAttributeSession(get(), createIfNull);
	}
	
	static Session getAttributeSession() {
		return getAttributeSession(get(), null);
	}
	
	static void setAttributeSession(Session value,Object session) {
		if(session == null)
			return;
		setAttributeValue(Session.class, value, session);
	}

	static String getUserName(Principal principal) {
		if(principal == null)
			return null;
		if(principal instanceof KeycloakPrincipal) {
			KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) principal;
			AccessToken accessToken = keycloakPrincipal.getKeycloakSecurityContext().getToken();
			return accessToken.getPreferredUsername();
		}
		return principal.getName();
	}
	
	static String getUserName() {
		return getUserName(SecurityHelper.getPrincipal());
	}
	
	static void destroy() {
		//TODO must environment based
		
		//in servlet world it consist to call logout to a request
		try {
			DependencyInjection.inject(HttpServletRequest.class).logout();
		} catch (ServletException exception) {
			throw new RuntimeException(exception);
		}
	}
}
