package org.cyk.utility.client.controller.web.jsf.page;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.cyk.utility.client.controller.component.window.AbstractWindowContainerManagedImpl;

public abstract class AbstractPageContainerManagedImpl extends AbstractWindowContainerManagedImpl implements PageContainerManaged,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object __getRequest__() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequest();/*TODO we can use injection*/
	}
}
