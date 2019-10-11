package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.servlet.ServletContext;

@Deprecated
public abstract class AbstractContextGetterImpl extends org.cyk.utility.context.AbstractContextGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object __execute__() throws Exception {
		return SERVLET_CONTEXT;
	}

	/**/
	
	public static ServletContext SERVLET_CONTEXT = null;
	
}
