package org.cyk.utility.__kernel__.session;

import java.io.Serializable;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.security.SecurityHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface SessionManager {

	Boolean isUserLogged(HttpServletRequest request);
	
	String getUserName(Principal principal);
	String getUserName();
	
	void destroy(String username,HttpServletRequest request);
	void destroy(String username);
	
	public static abstract class AbstractImpl extends AbstractObject implements SessionManager,Serializable {
		
		@Override
		public Boolean isUserLogged(HttpServletRequest request) {
			if(request == null)
				return Boolean.FALSE;
			return Boolean.TRUE.equals(__isUserLogged__(request));
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
