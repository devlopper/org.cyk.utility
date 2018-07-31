package org.cyk.utility.system;

import java.util.Collection;

import org.cyk.utility.service.ServiceProvider;
import org.cyk.utility.system.action.SystemAction;

public interface SystemServiceProvider extends ServiceProvider {

	/**
	 * Check if object is persisted.
	 * @param object
	 * @return
	 */
	Boolean isPersisted(Object object);
	
	/**
	 * Based on action we validate object using needed rules.</br>
	 * If action is null we use rules as object is persisted.</br>
	 * @param object
	 * @param action
	 * @return
	 */
	ServiceProvider validateOne(Object object,SystemAction action);
	
	ServiceProvider validateOne(Object object);
	
	ServiceProvider validateMany(Collection<Object> objects,SystemAction action);
	
	ServiceProvider validateMany(Collection<Object> objects);
}
