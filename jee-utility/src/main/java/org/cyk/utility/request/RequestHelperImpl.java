package org.cyk.utility.request;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RequestHelperImpl extends AbstractRequestHelperImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Object getProperty(Object request,RequestProperty property) {
		return null;
	}

}
