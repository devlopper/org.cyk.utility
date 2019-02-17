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
				Map<String,Set<NavigationCase>> map = (Map<String, Set<NavigationCase>>) Properties.getFromPath(getFunction().getProperties(),Properties.MAP);
				Object context = Properties.getFromPath(getFunction().getProperties(),Properties.CONTEXT);
				FacesContext facesContext = null;
				if(context instanceof FacesContext)
					facesContext = (FacesContext) context;
				Object identifier = getFunction().getIdentifier();
				if(identifier == null) {
					
				}
				
				if(identifier != null) {
					if(map == null) {
						if(facesContext == null)
							facesContext = FacesContext.getCurrentInstance();
						if(facesContext != null) {
							ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler)facesContext.getApplication().getNavigationHandler();
							if(configurableNavigationHandler!=null)
								map = configurableNavigationHandler.getNavigationCases();
						}
					}
					
					if(map == null)
						throw new RuntimeException("No navigation identifier map can be found to derive "+identifier);
					
					if(map != null) {	
						Boolean found = null;
						for(Map.Entry<String,Set<NavigationCase>> indexEntry : map.entrySet()) {
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
			}
		});
	}
	
}