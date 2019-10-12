package org.cyk.utility.server.deployment;

import java.io.Serializable;

import javax.servlet.ServletContext;

@Deprecated
public abstract class AbstractContextParameterValueGetterImpl extends org.cyk.utility.context.AbstractContextParameterValueGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object __execute__(Object context, String name) throws Exception {
		return ((ServletContext)context).getInitParameter(name);
	}
	
}