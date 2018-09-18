package org.cyk.utility.server.business;

import java.util.Collection;

import javax.transaction.Transactional;

import org.cyk.utility.system.action.SystemAction;

public interface BusinessFunctionTransaction extends BusinessFunction {

	@Override 
	@Transactional
	BusinessFunctionTransaction execute();
	
	@Override BusinessFunctionTransaction setEntity(Object entity);
	
	@Override BusinessFunctionTransaction setEntities(Collection<?> entities);
	
	@Override BusinessFunctionTransaction setAction(SystemAction action);
}
