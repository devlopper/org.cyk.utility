package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.request.RequestGetter;

public class RequestGetterRunnableImpl extends AbstractFunctionRunnableImpl<RequestGetter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public RequestGetterRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				setOutput(FacesContext.getCurrentInstance().getExternalContext().getRequest());
			}
		});
	}
	
	
}