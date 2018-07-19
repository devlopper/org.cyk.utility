package org.cyk.utility.server.business;

import java.util.Collection;

import org.cyk.utility.system.action.SystemAction;

public interface BusinessFunctionTransaction extends BusinessFunction {

	BusinessFunctionTransaction setEntity(Object entity);
	
	BusinessFunctionTransaction setEntities(Collection<?> entities);
	
	BusinessFunctionTransaction setAction(SystemAction action);
}
