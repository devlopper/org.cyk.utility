package org.cyk.utility.client.controller.web.jsf.page;

import java.io.Serializable;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;

import org.cyk.utility.client.controller.component.window.AbstractWindowContainerManagedImpl;

public abstract class AbstractPageContainerManagedImpl extends AbstractWindowContainerManagedImpl implements PageContainerManaged,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object __getContext__() {
		return FacesContext.getCurrentInstance(); /*TODO we can use injection*/
	}
	
	@Override
	protected Object __getNavigationIdentifierStringMap__() {
		return ((ConfigurableNavigationHandler)((FacesContext)__getContext__()).getApplication().getNavigationHandler()).getNavigationCases();
	}
	
	@Override
	protected Object __getRequest__() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequest(); /*TODO we can use injection*/
	}
}
