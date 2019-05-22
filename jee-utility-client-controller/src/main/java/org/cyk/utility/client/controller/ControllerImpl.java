package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.value.ValueUsageType;

@Singleton
public class ControllerImpl extends AbstractControllerServiceProviderImpl<Object> implements Controller,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public ControllerServiceProvider<Object> create(Object object, Properties properties) {
		ControllerEntity<Object> controller = (ControllerEntity<Object>)  __injectControllerLayer__().injectInterfaceClassFromEntity(object);
		if(controller == null){
			super.create(object, properties);
		}else{
			controller.create(object, properties);
		}
		return this;
	}
	
	/*@Override
	public ControllerServiceProvider<Object> createMany(Collection<Object> objects, Properties properties) {
		@SuppressWarnings("unchecked")
		Class<Object> aClass = (Class<Object>) objects.iterator().next().getClass();
		ControllerEntity<Object> business = (ControllerEntity<Object>)  __injectControllerLayer__().injectInterfaceClassFromEntityClass(aClass);
		if(business == null){
			super.createMany(objects, properties);
		}else{
			business.createMany(objects, properties);
		}
		return this;
	}*/
	
	@Override
	public <ENTITY> ENTITY readOne(Class<ENTITY> aClass, Object identifier, Properties properties) {
		ENTITY entity;
		ControllerEntity<ENTITY> controller = (ControllerEntity<ENTITY>)  __injectControllerLayer__().injectInterfaceClassFromEntityClass(aClass);
		if(controller == null){
			ControllerFunctionReader function = __inject__(ControllerFunctionReader.class);
			function.getProperties().copyFrom(properties, Properties.VALUE_USAGE_TYPE);
			function.copyProperty(Properties.REQUEST,properties);
			function.copyProperty(Properties.CONTEXT,properties);
			entity = (ENTITY) function.setEntityClass(aClass).setEntityIdentifier(identifier).execute().getProperties().getEntity();
		}else{
			entity = controller.readOne(identifier, properties);
		}
		return entity;
	}

	@Override
	public <ENTITY> ENTITY readOne(Class<ENTITY> aClass, Object identifier) {
		return readOne(aClass, identifier, null);
	}

	@Override
	public <ENTITY> Collection<ENTITY> readMany(Class<ENTITY> aClass, Properties properties) {
		Collection<ENTITY> entities;
		ControllerEntity<ENTITY> controller = (ControllerEntity<ENTITY>)  __injectControllerLayer__().injectInterfaceClassFromEntityClass(aClass);
		if(controller == null){
			ControllerFunctionReader function = __inject__(ControllerFunctionReader.class);
			function.getProperties().copyFrom(properties, Properties.VALUE_USAGE_TYPE);
			function.copyProperty(Properties.REQUEST,properties);
			function.copyProperty(Properties.CONTEXT,properties);
			//TODO paging should be handled like ControllerEntity
			entities = (Collection<ENTITY>) function.setActionEntityClass(aClass).execute().getProperties().getEntities();
		}else{
			entities = controller.readMany(properties);
		}
		return entities;
	}

	@Override
	public <ENTITY> Collection<ENTITY> readMany(Class<ENTITY> aClass) {
		//TODO handle pagination
		return readMany(aClass, null);
	}
	
	@Override
	public ControllerServiceProvider<Object> delete(Object object, Properties properties) {
		ControllerEntity<Object> controller = (ControllerEntity<Object>)  __injectControllerLayer__().injectInterfaceClassFromEntity(object);
		if(controller == null){
			super.delete(object, properties);
		}else{
			controller.delete(object, properties);
		}
		return this;
	}

	@Override
	public ControllerServiceProvider<Object> deleteAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Controller deleteAll(Collection<Class<?>> classes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Controller deleteAll(Class<?>... classes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <ENTITY> Controller deleteByClassByIdentififerByValueUsageType(Class<ENTITY> clazz, Object identifier,
			ValueUsageType valueUsageType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <ENTITY> Long count(Class<ENTITY> aClass, Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <ENTITY> Long count(Class<ENTITY> aClass) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public <ENTITY> Controller redirect(Class<ENTITY> aClass, Object identifier, Properties properties) {
		ControllerEntity<Object> controller = (ControllerEntity<Object>)  __injectControllerLayer__().injectInterfaceClassFromEntityClass(aClass);
		if(controller == null){
			__injectThrowableHelper__().throwRuntimeException("No controller found for <<"+aClass+">> to do redirect action");
		}else{
			controller.redirect(identifier, properties);
		}
		return this;
	}
	
	@Override
	public <ENTITY> Controller redirect(Class<ENTITY> aClass, Object identifier) {
		Properties properties = new Properties().setValueUsageType(ValueUsageType.BUSINESS);
		return redirect(aClass, identifier,properties);
	}
	
	/**/
	
	@Override
	public Controller act(SystemAction systemAction, Data data) {
		// TODO Auto-generated method stub
		return this;
	}
	
	/**/
	
	protected static ControllerLayer __injectControllerLayer__() {
		return __inject__(ControllerLayer.class);
	}

}
