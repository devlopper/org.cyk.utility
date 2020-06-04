package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface NavigationCaseGetter {

	NavigationCase get(String outcome);
	
	/**/
	
	static NavigationCaseGetter getInstance() {
		return Helper.getInstance(NavigationCaseGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements NavigationCaseGetter,Serializable {
		
		@Override
		public NavigationCase get(String outcome) {
			if(StringHelper.isBlank(outcome) == null)
				return null;
			return __get__(outcome);
		}
		
		private NavigationCase __get__(String outcome) {
			ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler)FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
			if(MapHelper.isNotEmpty(configurableNavigationHandler.getNavigationCases())) {
				for(Map.Entry<String,Set<NavigationCase>> entry : configurableNavigationHandler.getNavigationCases().entrySet()) {
					if(CollectionHelper.isEmpty(entry.getValue()))
						continue;
					for(NavigationCase navigationCase : entry.getValue()) {
						if(navigationCase.getFromOutcome().equals(outcome))
							return navigationCase;
					}
				}
			}
			return null;
		}
	}
}