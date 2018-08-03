package org.cyk.utility.service;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.assertion.AssertionBuilderNull;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.log.Log;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.throwable.ThrowableHelper;
import org.cyk.utility.value.ValueHelper;

public abstract class AbstractServiceProviderImpl extends AbstractSingleton implements ServiceProvider, Serializable {
	private static final long serialVersionUID = 1L;

	/**/
	
	/* short cut methods for service implementation */
	
	protected Log injectLog(SystemAction systemAction,Object object){
		return __inject__(Log.class).addMarkers(getLogMarkers(systemAction,object));
	}
	
	protected Collection<String> getLogMarkers(SystemAction systemAction,Object object){
		return systemAction == null ? null :__inject__(CollectionHelper.class).instanciate(systemAction.getIdentifier().toString(),object.getClass().getSimpleName());
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
	
	protected ValueHelper __injectValueHelper__(){
		return __inject__(ValueHelper.class);
	}
	
	protected CollectionHelper __injectCollectionHelper__(){
		return __inject__(CollectionHelper.class);
	}
	
	protected ThrowableHelper __injectThrowableHelper__(){
		return __inject__(ThrowableHelper.class);
	}
	
	protected StringHelper __injectStringHelper__(){
		return __inject__(StringHelper.class);
	}
	
	protected FieldValueGetter __injectFieldValueGetter__(){
		return __inject__(FieldValueGetter.class);
	}
	
	protected AssertionBuilderNull __injectAssertionBuilderNull__(){
		return __inject__(AssertionBuilderNull.class);
	}
	
	protected NumberHelper __injectNumberHelper__(){
		return __inject__(NumberHelper.class);
	}
}
