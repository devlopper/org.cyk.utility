package org.cyk.utility.system;

import java.util.Collection;

import org.cyk.utility.service.ServiceProvider;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public interface SystemServiceProvider extends ServiceProvider {

	/**
	 * Check if object is persisted.
	 * @param object
	 * @return
	 */ //TODO write a function for that
	Boolean isPersisted(Object object);
	
	/**
	 * Based on action we validate object using needed rules.</br>
	 * If action is null we use rules as object is persisted.</br>
	 * @param object
	 * @param action
	 * @return
	 */
	@Deprecated //Use Function Pre/Post Conditions
	ServiceProvider validateOne(Object object,SystemAction action);
	
	@Deprecated //Use Function Pre/Post Conditions
	ServiceProvider validateOne(Object object);
	
	@Deprecated //Use Function Pre/Post Conditions
	ServiceProvider validateMany(Collection<?> objects,SystemAction action);
	
	@Deprecated //Use Function Pre/Post Conditions
	ServiceProvider validateMany(Collection<?> objects);
}
