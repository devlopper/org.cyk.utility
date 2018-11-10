package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.client.controller.web.ComponentHelper;

public abstract class AbstractFunctionRunnableImpl<FUNCTION extends Function<?,?>> extends org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl<FUNCTION> implements Serializable {
	private static final long serialVersionUID = 1L;

	protected FacesContext __injectFacesContext__() {
		return FacesContext.getCurrentInstance();
	}
	
	protected ComponentHelper __injectComponentHelper__() {
		return __inject__(ComponentHelper.class);
	}
	
}
