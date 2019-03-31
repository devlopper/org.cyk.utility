package org.cyk.utility.client.controller.session;

import java.io.Serializable;

import javax.enterprise.inject.Default;

@Default
public class SessionAttributeGetterImpl extends AbstractSessionAttributeGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object __execute__(Object attribute,Object request) {
		__injectThrowableHelper__().throwRuntimeExceptionNotYetImplemented(getClass().getName());
		return null;
	}

}
