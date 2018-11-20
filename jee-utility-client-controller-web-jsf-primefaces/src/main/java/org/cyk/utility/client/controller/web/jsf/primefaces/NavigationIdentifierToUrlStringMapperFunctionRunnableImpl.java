package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierToUrlStringMapper;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierStringBuilder;

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
					Map<String,Set<NavigationCase>> map = configNavHandler.getNavigationCases();
					
					Boolean found = null;
					for(Map.Entry<String,Set<NavigationCase>> indexEntry : map.entrySet()) {
						for(NavigationCase indexNavigationCase : indexEntry.getValue()) {
							if(indexNavigationCase.getFromOutcome().equals(identifier.toString())) {
								found= Boolean.TRUE;
								
								String url = __inject__(UniformResourceIdentifierStringBuilder.class).setRequest(FacesContext.getCurrentInstance().getExternalContext().getRequest())
										.setPath(indexNavigationCase.getToViewId(FacesContext.getCurrentInstance()))
										.execute().getOutput();
								setOutput(url);	
								
								break;
							}
						}
						if(Boolean.TRUE.equals(found)) {
							break;
						}
					}
					/*
					NavigationCase navigationCase = configNavHandler.getNavigationCase(FacesContext.getCurrentInstance(),null,identifier.toString());
					if(navigationCase != null) {
						String url = __inject__(UniformResourceIdentifierStringBuilder.class).setRequest(FacesContext.getCurrentInstance().getExternalContext().getRequest())
								.setPath(navigationCase.getToViewId(FacesContext.getCurrentInstance()))
								.execute().getOutput();
						setOutput(url);	
					}
					*/
				}
			}
		});
	}
	
}