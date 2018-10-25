package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.cyk.utility.client.controller.component.window.AbstractWindowImpl;

public abstract class AbstractPageImpl extends AbstractWindowImpl implements Page,Serializable {
	private static final long serialVersionUID = 1L;

	protected <T> T ____getProxy____(Class<T> aClass) {
		return __getProxyByRequest__(aClass,FacesContext.getCurrentInstance().getExternalContext().getRequest()/*TODO we can use injection*/);
	}
	
}
