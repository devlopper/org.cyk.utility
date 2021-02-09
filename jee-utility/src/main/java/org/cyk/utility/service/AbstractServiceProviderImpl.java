package org.cyk.utility.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.log.LogLevel;
import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.enumeration.EnumCollectionGetter;
import org.cyk.utility.enumeration.EnumGetter;
import org.cyk.utility.enumeration.EnumerationHelper;
import org.cyk.utility.log.Log;
import org.cyk.utility.system.SystemFunction;
import org.cyk.utility.type.TypeHelper;

public abstract class AbstractServiceProviderImpl extends AbstractSingleton implements ServiceProvider, Serializable {
	private static final long serialVersionUID = 1L;

	/**/
	
	/* short cut methods for service implementation */
	
	protected Log injectLog(SystemAction systemAction,Object object){
		return __inject__(Log.class).addMarkers(getLogMarkers(systemAction,object));
	}
	
	protected Collection<String> getLogMarkers(SystemAction systemAction,Object object){
		return systemAction == null ? null :List.of(systemAction.getIdentifier().toString(),object.getClass().getSimpleName());
	}
	
	protected void __logTrace__(String message){
		__inject__(Log.class).executeTrace(message);
	}
	
	protected void __logInfo__(String message){
		__inject__(Log.class).executeInfo(message);
	}
	
	protected void __logWarn__(String message){
		__inject__(Log.class).executeWarn(message);
	}
	
	protected TypeHelper __injectTypeHelper__(){
		return __inject__(TypeHelper.class);
	}
	
	protected EnumerationHelper __injectEnumerationHelper__(){
		return __inject__(EnumerationHelper.class);
	}
	
	protected EnumGetter __injectEnumGetter__(){
		return __inject__(EnumGetter.class);
	}
	
	protected EnumCollectionGetter __injectEnumCollectionGetter__(){
		return __inject__(EnumCollectionGetter.class);
	}
	
	/**/
	
	/**/
	
	protected static void __copyCommonProperties__(SystemFunction function,Properties properties) {
		LogLevel logLevel = (LogLevel) Properties.getFromPath(properties, Properties.LOG_LEVEL);
		if(logLevel != null)
			function.getLog(Boolean.TRUE).setLevel(logLevel);
		if(properties != null) {
			function.getProperties().setFields(properties.getFields());
		}
	}

}
