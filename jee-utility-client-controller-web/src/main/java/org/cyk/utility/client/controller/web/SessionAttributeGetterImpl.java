package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.cyk.utility.__kernel__.annotation.Web;
import org.cyk.utility.client.controller.session.AbstractSessionAttributeGetterImpl;

@Web @Deprecated
public class SessionAttributeGetterImpl extends AbstractSessionAttributeGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Object __execute__(Object attribute, Object request) {
		Object value = null;
		//TODO how to make it work. it is throwing exception
		//if(request == null)
		//	request = __inject__(RequestGetter.class).execute().getOutput();
		if(request!=null) {
			HttpSession session = ((HttpServletRequest)request).getSession();
			if(session!=null)
				value = session.getAttribute((String)attribute);
		}
		return value;
	}
	
}