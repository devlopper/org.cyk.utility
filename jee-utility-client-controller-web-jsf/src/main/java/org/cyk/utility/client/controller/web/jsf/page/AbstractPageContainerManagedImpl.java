package org.cyk.utility.client.controller.web.jsf.page;

import java.io.Serializable;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;

import org.cyk.utility.client.controller.component.window.AbstractWindowContainerManagedImpl;

public abstract class AbstractPageContainerManagedImpl extends AbstractWindowContainerManagedImpl implements PageContainerManaged,Serializable {
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
}
