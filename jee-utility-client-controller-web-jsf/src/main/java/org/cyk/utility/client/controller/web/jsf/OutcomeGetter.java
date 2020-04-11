package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;

public interface OutcomeGetter {

	String get(Class<?> klass,Action action);
	
	/**/
	
	static OutcomeGetter getInstance() {
		return Helper.getInstance(OutcomeGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements OutcomeGetter,Serializable {
		
		@Override
		public String get(Class<?> klass, Action action) {
			if(klass == null)
				return null;
			if(action == null)
				return null;			
			String outcome = OutcomeBuilder.getInstance().build(klass, action);
			if(!__isNavigationCaseExist__(outcome)) {
				if(Action.CREATE.equals(action) || Action.READ.equals(action) || Action.UPDATE.equals(action) || Action.DELETE.equals(action))
					outcome = OutcomeBuilder.getInstance().build(klass, Action.EDIT);
			}
			if(StringHelper.isBlank(outcome))
				throw new RuntimeException(String.format("No outcome found for %s.%",klass,action));
			return outcome;
		}
		
		private Boolean __isNavigationCaseExist__(String outcome) {
			ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler)FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
			if(MapHelper.isNotEmpty(configurableNavigationHandler.getNavigationCases())) {
				for(Map.Entry<String,Set<NavigationCase>> entry : configurableNavigationHandler.getNavigationCases().entrySet()) {
					if(CollectionHelper.isEmpty(entry.getValue()))
						continue;
					for(NavigationCase navigationCase : entry.getValue())
						if(navigationCase.getFromOutcome().equals(outcome))
							return Boolean.TRUE;
				}
			}
			return Boolean.FALSE;
		}
	}
}