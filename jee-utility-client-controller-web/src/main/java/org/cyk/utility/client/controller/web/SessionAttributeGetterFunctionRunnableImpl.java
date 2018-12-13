package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.session.SessionAttributeGetter;
import org.cyk.utility.request.RequestGetter;

public class SessionAttributeGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<SessionAttributeGetter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public SessionAttributeGetterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				HttpServletRequest request = (HttpServletRequest) __inject__(RequestGetter.class).execute().getOutput();
				HttpSession session = request.getSession();
				if(session!=null)
					setOutput(session.getAttribute(getFunction().getAttribute().name()));
				
			}
		});
	}
	
}