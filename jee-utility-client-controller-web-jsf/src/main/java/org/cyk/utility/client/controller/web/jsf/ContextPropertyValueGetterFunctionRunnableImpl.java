package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;

import org.cyk.utility.client.controller.context.ContextPropertyName;
import org.cyk.utility.throwable.ThrowableHelper;

public class ContextPropertyValueGetterFunctionRunnableImpl extends AbstractContextPropertyValueGetterFunctionRunnableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	public ContextPropertyValueGetterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				Object context = getFunction().getContext();
				if(context == null)
					__inject__(ThrowableHelper.class).throwRuntimeException("Context is required");
				ContextPropertyName name = getFunction().getPropertyName();
				if(name == null)
					__inject__(ThrowableHelper.class).throwRuntimeException("Context property name is required");
				FacesContext facesContext = (FacesContext) context;
				switch(name) {
				case REQUEST :
					setOutput(facesContext.getExternalContext().getRequest());
					break;
				case UNIFORM_RESOURCE_LOCATOR_MAP:
					setOutput(((ConfigurableNavigationHandler)facesContext.getApplication().getNavigationHandler()).getNavigationCases());
					break;
				}
				
			}
		});
	}
	
}
