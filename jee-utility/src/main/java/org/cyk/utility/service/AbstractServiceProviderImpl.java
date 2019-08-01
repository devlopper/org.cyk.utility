package org.cyk.utility.service;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.assertion.AssertionBuilderNull;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.enumeration.EnumCollectionGetter;
import org.cyk.utility.enumeration.EnumGetter;
import org.cyk.utility.enumeration.EnumerationHelper;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.field.FieldValueSetter;
import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.log.Log;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.network.MailHelper;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.stream.distributed.Topic;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.system.SystemFunction;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.throwable.ThrowableHelper;
import org.cyk.utility.type.TypeHelper;
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
	
	protected InstanceHelper __injectInstanceHelper__(){
		return __inject__(InstanceHelper.class);
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
	
	protected FieldValueSetter __injectFieldValueSetter__(){
		return __inject__(FieldValueSetter.class);
	}
	
	protected FieldHelper __injectFieldHelper__(){
		return __inject__(FieldHelper.class);
	}
	
	protected ClassHelper __injectClassHelper__(){
		return __inject__(ClassHelper.class);
	}
	
	protected AssertionBuilderNull __injectAssertionBuilderNull__(){
		return __inject__(AssertionBuilderNull.class);
	}
	
	protected NumberHelper __injectNumberHelper__(){
		return __inject__(NumberHelper.class);
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
	
	protected MailHelper __injectMailHelper__(){
		return __inject__(MailHelper.class);
	}
	
	/**/
	
	protected void __sendMail__(String title,String body,Collection<Object> receiversIdentifiers,Boolean isExecuteAsynchronously) {
		__injectMailHelper__().send(title, body, receiversIdentifiers, isExecuteAsynchronously);
	}
	
	protected void __produceMail__(String title,String body,Collection<String> receiversIdentifiers) {
		if(Boolean.TRUE.equals(Topic.MAIL.getIsConsumerStarted()))
			__injectMailHelper__().produce(title, body, receiversIdentifiers);
		else {
			try {
				__sendMail__(title, body, __inject__(CollectionHelper.class).cast(Object.class, receiversIdentifiers), Boolean.FALSE);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
	
	protected void __throwRuntimeExceptionIfNull__(Object value, String message) {
		__injectThrowableHelper__().throwRuntimeExceptionIfNull(value, message);
	}
	
	protected void __throwRuntimeExceptionIfEmpty__(Object value, String message) {
		__injectThrowableHelper__().throwRuntimeExceptionIfEmpty(value, message);
	}
	
	protected void __throwRuntimeExceptionIfBlank__(Object value, String message) {
		__injectThrowableHelper__().throwRuntimeExceptionIfBlank(value, message);
	}
	
	/**/
	
	protected static void __copyCommonProperties__(SystemFunction function,Properties properties) {
		LogLevel logLevel = (LogLevel) Properties.getFromPath(properties, Properties.LOG_LEVEL);
		if(logLevel != null)
			function.getLog(Boolean.TRUE).setLevel(logLevel);
		if(properties != null)
			function.getProperties().setFields(properties.getValue());
	}

}
