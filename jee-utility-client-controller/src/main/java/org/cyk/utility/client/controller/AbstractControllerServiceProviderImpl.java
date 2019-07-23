package org.cyk.utility.client.controller;
import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.AbstractSystemServiceProviderImpl;

public abstract class AbstractControllerServiceProviderImpl<OBJECT> extends AbstractSystemServiceProviderImpl implements ControllerServiceProvider<OBJECT>, Serializable {
	private static final long serialVersionUID = 1L;

	/**/
	
	protected Boolean __isCreateManyOneByOne__() {
		return Boolean.TRUE;
	}
	
	@Override
	public ControllerServiceProvider<OBJECT> create(OBJECT object,Properties properties) {
		if(properties == null)
			properties = new Properties();
		ControllerFunctionCreator function = ____inject____(ControllerFunctionCreator.class);
		function.setEntity(object);
		//function.getAction().getEntities(Boolean.TRUE).add(object);
		function.copyProperty(Properties.REQUEST,properties);
		function.copyProperty(Properties.CONTEXT,properties);
		function.execute();
		if(properties!=null) {
			properties.setResponse(function.getProperties().getResponse());
			properties.setAction(function.getProperties().getAction());
		}
		return this;
	}
	
	@Override
	public ControllerServiceProvider<OBJECT> create(OBJECT object) {
		return create(object, null);
	}
	
	@Override
	public ControllerServiceProvider<OBJECT> update(OBJECT object, Properties properties) {
		if(properties == null)
			properties = new Properties();
		ControllerFunctionModifier function = ____inject____(ControllerFunctionModifier.class);
		function.setEntity(object);
		function.copyProperty(Properties.REQUEST,properties);
		function.copyProperty(Properties.CONTEXT,properties);
		function.copyProperty(Properties.FIELDS,properties);
		function.execute();
		if(properties!=null) {
			properties.setResponse(function.getProperties().getResponse());
			properties.setAction(function.getProperties().getAction());
		}
		return this;
	}

	@Override
	public ControllerServiceProvider<OBJECT> update(OBJECT object) {
		return update(object, null);
	}

	@Override
	public ControllerServiceProvider<OBJECT> delete(OBJECT object, Properties properties) {
		if(properties == null)
			properties = new Properties();
		ControllerFunctionRemover function = ____inject____(ControllerFunctionRemover.class);
		//Object identifier = __injectFieldHelper__().getFieldValueBusinessIdentifier(object);
		function.setEntity(object);
		//function.copyProperty(Properties.VALUE_USAGE_TYPE,properties);
		function.copyProperty(Properties.REQUEST,properties);
		function.copyProperty(Properties.CONTEXT,properties);
		function.execute();
		if(properties!=null) {
			properties.setResponse(function.getProperties().getResponse());
			properties.setAction(function.getProperties().getAction());
		}
		return this;
	}

	@Override
	public ControllerServiceProvider<OBJECT> delete(OBJECT object) {
		return delete(object, null);
	}
	
	@Override
	public ControllerServiceProvider<OBJECT> deleteAll(Properties properties) {
		__injectThrowableHelper__().throwRuntimeExceptionNotYetImplemented();
		return null;
	}
	
	@Override
	public ControllerServiceProvider<OBJECT> deleteAll() {
		return deleteAll(null);
	}

	@Override
	public ControllerServiceProvider<OBJECT> process(OBJECT object, Properties properties) {
		return null;
	}
	
	@Override
	public ControllerServiceProvider<OBJECT> process(OBJECT object) {
		return null;
	}
	
	/**/
	
	protected static ControllerLayer __injectPersistenceLayer__() {
		return __inject__(ControllerLayer.class);
	}
	
	protected static void __copyReadProperties__(ControllerFunction function,Properties properties) {
		function.copyProperty(Properties.REQUEST,properties);
		function.copyProperty(Properties.CONTEXT,properties);
		
		function.copyProperty(Properties.VALUE_USAGE_TYPE,properties);
		function.copyProperty(Properties.IS_PAGEABLE,properties);
		function.copyProperty(Properties.FROM,properties);
		function.copyProperty(Properties.COUNT,properties);
		function.copyProperty(Properties.FILTERS,properties);
		function.copyProperty(Properties.FIELDS,properties);
	}
	
	protected static void __copyCountProperties__(ControllerFunction function,Properties properties) {
		function.copyProperty(Properties.REQUEST,properties);
		function.copyProperty(Properties.CONTEXT,properties);
		
		function.copyProperty(Properties.FIELDS,properties);
	}
}
