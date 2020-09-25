package org.cyk.utility.__kernel__.identifier.resource;

import java.io.Serializable;

import javax.faces.context.FacesContext;

public class RequestGetterImpl extends RequestGetter.AbstractImpl implements Serializable {

	@Override
	public Object get() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

}
