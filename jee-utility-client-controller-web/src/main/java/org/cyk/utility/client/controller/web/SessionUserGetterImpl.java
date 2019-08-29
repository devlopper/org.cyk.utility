package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.cyk.utility.__kernel__.annotation.Web;
import org.cyk.utility.client.controller.session.SessionUser;
import org.cyk.utility.client.controller.session.SessionUserGetter;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.random.RandomHelper;

@Web
public class SessionUserGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<SessionUser> implements SessionUserGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected SessionUser __execute__() throws Exception {
		SessionUser sessionUser = null;
		HttpServletRequest request = __inject__(HttpServletRequest.class);
		HttpSession session = request.getSession();
		if(session != null) {
			String attributeName = SessionUser.class.getName();
			sessionUser = (SessionUser) session.getAttribute(attributeName);
			if(sessionUser == null) {
				sessionUser = __inject__(SessionUser.class);
				sessionUser.setIdentifier(__inject__(RandomHelper.class).getAlphanumeric(10));
				sessionUser.setPrincipal(request.getUserPrincipal());
				session.setAttribute(attributeName, sessionUser);
			}
		}
		return sessionUser;
	}
	
}
