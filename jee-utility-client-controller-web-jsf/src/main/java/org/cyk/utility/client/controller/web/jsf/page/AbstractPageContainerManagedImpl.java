package org.cyk.utility.client.controller.web.jsf.page;

import java.io.Serializable;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;

import org.cyk.utility.client.controller.web.jsf.JavaServerFacesHelper;

public abstract class AbstractPageContainerManagedImpl extends org.cyk.utility.client.controller.web.page.AbstractPageContainerManagedImpl implements PageContainerManaged,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object __getContext__() {
		return __getFacesContext__();
	}
	
	@Override
	protected Object __getUniformResourceLocatorMap__() {
		return ((ConfigurableNavigationHandler)__getFacesContext__().getApplication().getNavigationHandler()).getNavigationCases();
	}
	
	@Override
	protected Object __getRequest__() {
		return __getFacesContext__().getExternalContext().getRequest();
	}
	
	/**/
	
	protected FacesContext __getFacesContext__() {
		return FacesContext.getCurrentInstance(); /*TODO we can use injection*/
	}
	
	protected static final JavaServerFacesHelper __injectJavaServerFacesHelper__() {
		return __inject__(JavaServerFacesHelper.class);
	}
}
