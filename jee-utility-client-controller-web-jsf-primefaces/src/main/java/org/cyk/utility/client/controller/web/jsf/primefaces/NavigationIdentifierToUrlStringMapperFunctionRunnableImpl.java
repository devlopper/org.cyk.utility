package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierToUrlStringMapper;

public class NavigationIdentifierToUrlStringMapperFunctionRunnableImpl extends AbstractFunctionRunnableImpl<NavigationIdentifierToUrlStringMapper> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public NavigationIdentifierToUrlStringMapperFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				Object identifier = getFunction().getIdentifier();
				if(identifier == null) {
					
				}
				
				if(identifier != null) {
					ConfigurableNavigationHandler configNavHandler = (ConfigurableNavigationHandler)FacesContext.getCurrentInstance().getApplication().getNavigationHandler(); //assumes you already have an instance of FacesContext, named ctxt
					NavigationCase navigationCase = configNavHandler.getNavigationCase(FacesContext.getCurrentInstance(),null,identifier.toString());
					if(navigationCase != null) {
						String url = navigationCase.getToViewId(FacesContext.getCurrentInstance());
						HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
						url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+url;
						setOutput(url);	
					}
				}
			}
		});
	}
	
}