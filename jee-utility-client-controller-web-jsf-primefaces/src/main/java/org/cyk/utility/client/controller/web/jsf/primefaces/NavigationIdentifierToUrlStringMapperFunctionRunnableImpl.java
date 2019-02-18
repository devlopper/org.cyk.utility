package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierToUrlStringMapper;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierStringBuilder;

public class NavigationIdentifierToUrlStringMapperFunctionRunnableImpl extends AbstractFunctionRunnableImpl<NavigationIdentifierToUrlStringMapper> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public NavigationIdentifierToUrlStringMapperFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				@SuppressWarnings("unchecked")
				Map<String,Set<NavigationCase>> uniformResourceLocatorMap = (Map<String, Set<NavigationCase>>) Properties.getFromPath(getFunction().getProperties(),Properties.UNIFORM_RESOURCE_LOCATOR_MAP);
				Object context = Properties.getFromPath(getFunction().getProperties(),Properties.CONTEXT);
				FacesContext facesContext = null;
				if(context instanceof FacesContext)
					facesContext = (FacesContext) context;
				Object identifier = getFunction().getIdentifier();
				if(identifier == null) {
					
				}
				
				if(identifier != null) {
					if(uniformResourceLocatorMap == null) {
						if(facesContext == null)
							facesContext = FacesContext.getCurrentInstance();
						if(facesContext != null) {
							ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler)facesContext.getApplication().getNavigationHandler();
							if(configurableNavigationHandler!=null)
								try {
									uniformResourceLocatorMap = configurableNavigationHandler.getNavigationCases();
								} catch (Exception exception) {
									throw new RuntimeException("something goes wrong when trying to get navigation cases to derive "+identifier);
								}
						}
					}
					
					if(uniformResourceLocatorMap == null)
						throw new RuntimeException("No uniform resource locator map can be found to derive "+identifier);
					
					Boolean found = null;
					for(Map.Entry<String,Set<NavigationCase>> indexEntry : uniformResourceLocatorMap.entrySet()) {
						for(NavigationCase indexNavigationCase : indexEntry.getValue()) {
							if(indexNavigationCase.getFromOutcome().equals(identifier.toString())) {
								found= Boolean.TRUE;
								
								String url = __inject__(UniformResourceIdentifierStringBuilder.class).setRequest(facesContext.getExternalContext().getRequest())
										.setPath(indexNavigationCase.getToViewId(facesContext))
										.execute().getOutput();
								setOutput(url);	
								
								break;
							}
						}
						if(Boolean.TRUE.equals(found)) {
							break;
						}
					}
					
				}
			}
		});
	}
	
}