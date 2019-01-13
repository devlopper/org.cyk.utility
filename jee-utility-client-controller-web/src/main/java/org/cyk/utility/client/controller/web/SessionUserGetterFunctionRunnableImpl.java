package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.session.SessionUser;
import org.cyk.utility.client.controller.session.SessionUserGetter;
import org.cyk.utility.request.RequestGetter;

public class SessionUserGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<SessionUserGetter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public SessionUserGetterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				HttpServletRequest request = (HttpServletRequest) __inject__(RequestGetter.class).execute().getOutput();
				Object principal = request.getUserPrincipal();
				if(principal!=null)
					setOutput(__inject__(SessionUser.class));
			}
		});
	}
	
}