package org.cyk.utility.client.controller.session;

import java.io.Serializable;

import javax.enterprise.inject.Default;

@Default
public class SessionAttributeSetterImpl extends AbstractSessionAttributeSetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(Object attribute, Object value, Object request) {
		__injectThrowableHelper__().throwRuntimeExceptionNotYetImplemented();
	}
	
}
