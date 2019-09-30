package org.cyk.utility.request;

import java.io.Serializable;
import java.security.Principal;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;

import org.cyk.utility.__kernel__.throwable.ThrowableHelper;

@Dependent @Default
public class RequestPrincipalGetterImpl extends AbstractRequestPrincipalGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Principal __execute__(Object request) {
		ThrowableHelper.throwNotYetImplemented(getClass().getName());
		return null;
	}

}
