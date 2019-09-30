package org.cyk.utility.client.controller.session;

import java.io.Serializable;

import javax.enterprise.inject.Default;

import org.cyk.utility.__kernel__.throwable.ThrowableHelper;

@Default @Deprecated
public class SessionAttributeGetterImpl extends AbstractSessionAttributeGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object __execute__(Object attribute,Object request) {
		ThrowableHelper.throwNotYetImplemented(getClass().getName());
		return null;
	}

}
