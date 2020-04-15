package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.request.RequestGetter;
import org.cyk.utility.throwable.ThrowableHelper;

@Deprecated
public class RequestGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<RequestGetter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public RequestGetterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				if(FacesContext.getCurrentInstance() == null)
					__inject__(ThrowableHelper.class).throwRuntimeException("We cannot get request because FacesContext current instance is null.");
				setOutput(FacesContext.getCurrentInstance().getExternalContext().getRequest());
			}
		});
	}
	
	
}