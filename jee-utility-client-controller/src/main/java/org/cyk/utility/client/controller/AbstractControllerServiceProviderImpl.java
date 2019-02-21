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
	public ControllerServiceProvider<OBJECT> deleteAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ControllerServiceProvider<OBJECT> select(OBJECT object, Properties properties) {
		return null;
	}
	
	@Override
	public ControllerServiceProvider<OBJECT> select(OBJECT object) {
		return null;
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
	
}
