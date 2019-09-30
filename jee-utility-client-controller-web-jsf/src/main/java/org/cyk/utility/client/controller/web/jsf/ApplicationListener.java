package org.cyk.utility.client.controller.web.jsf;
import java.util.Map;
import java.util.Set;

import javax.faces.application.Application;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;

public class ApplicationListener implements SystemEventListener {

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
    	if (event instanceof PostConstructApplicationEvent){
    		//set all navigations cases to uniform resource identifier helper
        	ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) ((Application)event.getSource()).getNavigationHandler();
			for(Map.Entry<String,Set<NavigationCase>> indexEntry : configurableNavigationHandler.getNavigationCases().entrySet()) {
				for(NavigationCase indexNavigationCase : indexEntry.getValue()) {
					//String toViewId = indexNavigationCase.getToViewId(null);//TODO FacesContext is needed to avoid NPE
					String toViewId = (String) FieldHelper.read(indexNavigationCase, "toViewId");
					UniformResourceIdentifierHelper.setPathByIdentifier(indexNavigationCase.getFromOutcome(), toViewId);
				}
			}
        } else if (event instanceof PreDestroyApplicationEvent){
            
        }
    }

    @Override
    public boolean isListenerForSource(Object source) {
        return source instanceof Application;
    }
}