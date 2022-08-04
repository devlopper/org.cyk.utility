package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.request.RequestParameterValueGetter;

@Deprecated
public class RequestParameterValueGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<RequestParameterValueGetter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public RequestParameterValueGetterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				Object parameterName = getFunction().getParameterName();
				if(parameterName!=null) {
					HttpServletRequest request = (HttpServletRequest) getFunction().getRequest();
					if(request == null)
						request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
					//TODO we must handle multi value by calling getParameterValues which return an array
					setOutput(request.getParameter(parameterName.toString()));
				}
			}
		});
	}
	
}