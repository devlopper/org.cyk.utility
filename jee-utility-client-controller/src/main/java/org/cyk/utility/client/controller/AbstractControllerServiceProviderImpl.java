package org.cyk.utility.client.controller;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.controller.EntitySaver;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.system.AbstractSystemServiceProviderImpl;

public abstract class AbstractControllerServiceProviderImpl<OBJECT> extends AbstractSystemServiceProviderImpl implements ControllerServiceProvider<OBJECT>, Serializable {
	private static final long serialVersionUID = 1L;

	/**/
	
	protected Boolean __isCreateManyOneByOne__() {
		return Boolean.TRUE;
	}
	
	@Override
	public ControllerServiceProvider<OBJECT> createMany(Collection<OBJECT> objects, Properties properties) {
		if(properties == null)
			properties = new Properties();
		ControllerFunctionCreator function = ____inject____(ControllerFunctionCreator.class);
		EntitySaver.AbstractImpl.setAuditableWhoDoneWhatWhen(objects, null);
		function.setEntities(objects);
		//function.getAction().getEntities(Boolean.TRUE).add(object);
		function.copyProperty(Properties.REQUEST,properties);
		function.copyProperty(Properties.CONTEXT,properties);
		function.copyProperty(Properties.IS_BATCHABLE,properties);
		function.copyProperty(Properties.BATCH_SIZE,properties);
		function.execute();
		if(properties!=null) {
			properties.setResponse(function.getProperties().getResponse());
			properties.setAction(function.getProperties().getAction());
		}
		return this;
	}
	
	@Override
	public ControllerServiceProvider<OBJECT> createMany(Collection<OBJECT> objects) {
		return createMany(objects, null);
	}
	
	@Override
	public ControllerServiceProvider<OBJECT> create(OBJECT object,Properties properties) {
		createMany(Arrays.asList(object), properties);
		return this;
	}
	
	@Override
	public ControllerServiceProvider<OBJECT> create(OBJECT object) {
		return create(object, null);
	}
	
	@Override
	public ControllerServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects, Properties properties) {
		if(properties == null)
			properties = new Properties();
		EntitySaver.AbstractImpl.setAuditableWhoDoneWhatWhen(objects, null);
		ControllerFunctionModifier function = ____inject____(ControllerFunctionModifier.class);
		function.setEntities(objects);
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
	public ControllerServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects) {
		if(CollectionHelper.isEmpty(objects))
			return this;
		updateMany(objects, null);
		return this;
	}
	
	@Override
	public ControllerServiceProvider<OBJECT> update(OBJECT object, Properties properties) {
		updateMany(Arrays.asList(object), properties);
		return this;
	}

	@Override
	public ControllerServiceProvider<OBJECT> update(OBJECT object) {
		return update(object, null);
	}

	@Override
	public ControllerServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects, Properties properties) {
		if(properties == null)
			properties = new Properties();
		EntitySaver.AbstractImpl.setAuditableWhoDoneWhatWhen(objects, null);
		ControllerFunctionRemover function = ____inject____(ControllerFunctionRemover.class);
		function.setEntities(objects);
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
	public ControllerServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects) {
		return deleteMany(objects, null);
	}
	
	@Override
	public ControllerServiceProvider<OBJECT> delete(OBJECT object, Properties properties) {
		deleteMany(Arrays.asList(object), properties);
		return this;
	}

	@Override
	public ControllerServiceProvider<OBJECT> delete(OBJECT object) {
		return delete(object, null);
	}
	
	@Override
	public ControllerServiceProvider<OBJECT> deleteAll(Properties properties) {
		ThrowableHelper.throwNotYetImplemented();
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
		__copyCommonProperties__(function, properties);
		function.copyProperty(Properties.REQUEST,properties);
		function.copyProperty(Properties.CONTEXT,properties);
		
		function.copyProperty(Properties.VALUE_USAGE_TYPE,properties);
		function.copyProperty(Properties.QUERY_IDENTIFIER,properties);
		function.copyProperty(Properties.IS_PAGEABLE,properties);
		function.copyProperty(Properties.FROM,properties);
		function.copyProperty(Properties.COUNT,properties);
		function.copyProperty(Properties.FILTERS,properties);
		function.copyProperty(Properties.FIELDS,properties);
	}
	
	protected static void __copyCountProperties__(ControllerFunction function,Properties properties) {
		function.copyProperty(Properties.REQUEST,properties);
		function.copyProperty(Properties.CONTEXT,properties);
		
		function.copyProperty(Properties.VALUE_USAGE_TYPE,properties);
		function.copyProperty(Properties.QUERY_IDENTIFIER,properties);
		function.copyProperty(Properties.FILTERS,properties);
		function.copyProperty(Properties.FIELDS,properties);
	}
}
