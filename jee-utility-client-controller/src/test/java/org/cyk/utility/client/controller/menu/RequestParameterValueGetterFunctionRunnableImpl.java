package org.cyk.utility.client.controller.menu;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.request.RequestParameterValueGetter;

public class RequestParameterValueGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<RequestParameterValueGetter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static Request REQUEST = null;
	
	public RequestParameterValueGetterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				Object parameterName = getFunction().getParameterName();
				if(parameterName!=null) {
					Request request = (Request) getFunction().getRequest();
					if(request == null)
						request = REQUEST;
					setOutput(request.getParameter(parameterName.toString()));
				}
			}
		});
	}
	
}