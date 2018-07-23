package org.cyk.utility.service;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.log.Log;
import org.cyk.utility.system.action.SystemAction;

public abstract class AbstractServiceProviderImpl extends AbstractSingleton implements ServiceProvider, Serializable {
	private static final long serialVersionUID = 1L;

	/* short cut methods for service implementation */
	
	protected Log injectLog(SystemAction systemAction,Object object){
		return __inject__(Log.class).addMarkers(getLogMarkers(systemAction,object));
	}
	
	protected Collection<String> getLogMarkers(SystemAction systemAction,Object object){
		return systemAction == null ? null :__inject__(CollectionHelper.class).instanciate(systemAction.getIdentifier().toString(),object.getClass().getSimpleName());
	}
	
	protected void __logInfo__(String message){
		__inject__(Log.class).executeInfo(message);
	}
	
	protected void __logWarn__(String message){
		__inject__(Log.class).executeWarn(message);
	}
}
