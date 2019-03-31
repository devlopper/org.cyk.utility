package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.cyk.utility.__kernel__.annotation.Web;
import org.cyk.utility.client.controller.session.AbstractSessionAttributeSetterImpl;

@Web
public class SessionAttributeSetterImpl extends AbstractSessionAttributeSetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	@Override
	protected void __execute__(Object attribute, Object value, Object request) {
		//How to make it Work. it is throwing exception
		//if(request == null)
		//	request = __inject__(RequestGetter.class).execute().getOutput();
		if(request!=null) {
			HttpSession session = ((HttpServletRequest)request).getSession();
			if(session!=null)
				session.setAttribute((String)attribute, value);
		}
	}
}