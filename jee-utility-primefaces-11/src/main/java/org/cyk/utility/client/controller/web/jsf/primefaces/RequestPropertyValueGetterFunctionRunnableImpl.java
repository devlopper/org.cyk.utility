package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.identifier.resource.RequestProperty;
import org.cyk.utility.request.RequestPropertyValueGetter;
@Deprecated //TODO to be deleted. use for utility instead
public class RequestPropertyValueGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<RequestPropertyValueGetter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public RequestPropertyValueGetterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				HttpServletRequest request = (HttpServletRequest) getFunction().getRequest();
				if(request!=null) {
					RequestProperty property = getFunction().getProperty();
					if(RequestProperty.SCHEME.equals(property))
						setOutput(request.getScheme());
					else if(RequestProperty.HOST.equals(property))
						setOutput(request.getServerName());
					if(RequestProperty.PORT.equals(property))
						setOutput(request.getServerPort());
					if(RequestProperty.CONTEXT.equals(property))
						setOutput(request.getContextPath());
				}
				
			}
		});
	}
	
}