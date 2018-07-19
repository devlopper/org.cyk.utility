package org.cyk.utility.system;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;
import org.cyk.utility.system.action.SystemAction;

public interface SystemFunction extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	SystemAction getAction();
	SystemFunction setAction(SystemAction action);
	
	SystemFunction setEntityClass(Class<?> aClass);
	Class<?> getEntityClass();
	
	Object getEntity();
	SystemFunction setEntity(Object entity);
	
	SystemFunction setEntityIdentifier(Object identifier);
	Object getEntityIdentifier();
	
	Collection<?> getEntities();
	SystemFunction setEntities(Collection<?> entities);
}
