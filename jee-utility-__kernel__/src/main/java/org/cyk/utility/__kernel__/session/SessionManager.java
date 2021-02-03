package org.cyk.utility.__kernel__.session;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.security.SecurityHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface SessionManager {

	Boolean isUserLogged(HttpServletRequest request);
	Boolean isUserLogged();
	
	String getUserName(Principal principal);
	String getUserName();
	
	Boolean isUserHasRole(String role,HttpServletRequest request);
	Boolean isUserHasRole(String role);
	
	Boolean isUserHasOneOfRoles(Collection<String> roles,HttpServletRequest request);
	Boolean isUserHasOneOfRoles(Collection<String> roles);
	Boolean isUserHasOneOfRoles(String...roles);
	
	void writeAttribute(Object identifier,Object value,HttpServletRequest request);
	void writeAttribute(Object identifier,Object value);
	
	Object readAttribute(Object identifier,HttpServletRequest request);
	Object readAttribute(Object identifier);
	
	void removeAttribute(Object identifier,HttpServletRequest request);
	void removeAttribute(Object identifier);
	
	void destroy(String username,HttpServletRequest request);
	void destroy(String username);
	
	public static abstract class AbstractImpl extends AbstractObject implements SessionManager,Serializable {
		
		@Override
		public void writeAttribute(Object identifier,Object value,HttpServletRequest request) {
			if(identifier == null)
				return;
			HttpSession session = (request == null ? __inject__(HttpServletRequest.class) : request).getSession(Boolean.TRUE);
			if(session == null)
				return;
			session.setAttribute(identifier.toString(), value);
		}
		
		@Override
		public void writeAttribute(Object identifier, Object value) {
			writeAttribute(identifier, value, __inject__(HttpServletRequest.class));			
		}
		
		@Override
		public Object readAttribute(Object identifier,HttpServletRequest request) {
			if(identifier == null)
				return null;
			HttpSession session = (request == null ? __inject__(HttpServletRequest.class) : request).getSession();
			if(session == null)
				return null;
			return session.getAttribute(identifier.toString());
		}
		
		@Override
		public Object readAttribute(Object identifier) {
			return readAttribute(identifier,  __inject__(HttpServletRequest.class));
		}
		
		@Override
		public void removeAttribute(Object identifier, HttpServletRequest request) {
			if(identifier == null)
				return;
			HttpSession session = (request == null ? __inject__(HttpServletRequest.class) : request).getSession();
			if(session == null)
				return;
			session.removeAttribute(identifier.toString());
		}
		
		@Override
		public void removeAttribute(Object identifier) {
			removeAttribute(identifier, __inject__(HttpServletRequest.class));
		}
		
		@Override
		public Boolean isUserLogged(HttpServletRequest request) {
			if(request == null)
				return Boolean.FALSE;
			return Boolean.TRUE.equals(__isUserLogged__(request));
		}
		
		@Override
		public Boolean isUserLogged() {
			return isUserLogged(__inject__(HttpServletRequest.class));
		}
		
		protected Boolean __isUserLogged__(HttpServletRequest request) {
			return request.getUserPrincipal() != null;
		}
		
		public String getUserName(Principal principal) {
			if(principal == null)
				return null;
			return principal.getName();
		}
		
		@Override
		public String getUserName() {
			return getUserName(SecurityHelper.getPrincipal());
		}
		
		@Override
		public Boolean isUserHasRole(String role,HttpServletRequest request) {
			if(StringHelper.isBlank(role) || request == null)
				return null;
			return __isUserHasRole__(role,request);
		}
		
		@Override
		public Boolean isUserHasRole(String role) {
			return isUserHasRole(role, __inject__(HttpServletRequest.class));
		}
		
		protected Boolean __isUserHasRole__(String role,HttpServletRequest request) {
			return request.isUserInRole(role);
		}
		
		@Override
		public Boolean isUserHasOneOfRoles(Collection<String> roles, HttpServletRequest request) {
			if(CollectionHelper.isEmpty(roles))
				return null;
			for(String role : roles)
				if(Boolean.TRUE.equals(isUserHasRole(role, request)))
					return Boolean.TRUE;
			return null;
		}
		
		@Override
		public Boolean isUserHasOneOfRoles(Collection<String> roles) {
			if(CollectionHelper.isEmpty(roles))
				return null;
			return isUserHasOneOfRoles(roles, __inject__(HttpServletRequest.class));
		}
		
		@Override
		public Boolean isUserHasOneOfRoles(String... roles) {
			if(ArrayHelper.isEmpty(roles))
				return null;
			return isUserHasOneOfRoles(CollectionHelper.listOf(roles));
		}
		
		@Override
		public void destroy(String username,HttpServletRequest request) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("logged in user request", request);
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("logged in user session name", username);
			LogHelper.logInfo(String.format("Destroying session of <<%s>>", username) , SessionHelper.class);
			__destroy__(username, request);
			LogHelper.logInfo(String.format("Session of <<%s>> destroyed", username) , SessionHelper.class);
		}
	
		protected void __destroy__(String username,HttpServletRequest request) {
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
		}
		
		@Override
		public void destroy(String username) {
			destroy(username, __inject__(HttpServletRequest.class));
		}
	}
	
	/**/
	
	static SessionManager getInstance() {
		return Helper.getInstance(SessionManager.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}
