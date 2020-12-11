package org.cyk.utility.__kernel__.session;

import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.identifier.resource.RequestHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.security.SecurityHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
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
	
	static void destroy(HttpServletRequest request,String username) {
		ThrowableHelper.throwIllegalArgumentExceptionIfNull("logged in user request", request);
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("logged in user session name", username);
		LogHelper.logInfo(String.format("Destroying session of <<%s>>", username) , SessionHelper.class);
		//TODO must environment based
		
		// Servlet
		
		//invalidates the session but doesn't affect the identity information in the request
		HttpSession httpSession = request.getSession();
		ThrowableHelper.throwIllegalArgumentExceptionIfNull("logged in user session", httpSession);
		httpSession.invalidate();		
		LogHelper.logInfo(String.format("Session of <<%s>> has been invalidated",username), SessionHelper.class);
		
		//clears the identity information in the request but doesn't affect the session
		if(StringHelper.isNotBlank(request.getRemoteUser())) {
			try {
				request.logout();
				LogHelper.logInfo(String.format("Request of <<%s>> has been logged out",username), SessionHelper.class);
			} catch (ServletException exception) {
				LogHelper.log(exception, SessionHelper.class);
			}
		}
		
		//keycloak
		
		if (request.getAttribute(KeycloakSecurityContext.class.getName()) instanceof RefreshableKeycloakSecurityContext) {
			RefreshableKeycloakSecurityContext keycloakSecurityContext = (RefreshableKeycloakSecurityContext)request.getAttribute(KeycloakSecurityContext.class.getName());
			keycloakSecurityContext.logout(keycloakSecurityContext.getDeployment());
			request.removeAttribute(KeycloakSecurityContext.class.getName());
			LogHelper.logInfo(String.format("Keycloak Security Context of <<%s>> has been logged out and removed",username), SessionHelper.class);
		}
		LogHelper.logInfo(String.format("Session of <<%s>> destroyed", username) , SessionHelper.class);
	}
	
	static void destroy(String username) {
		destroy(DependencyInjection.inject(HttpServletRequest.class), username);
	}
}