package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.value.ValueUsageType;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <ENTITY> ENTITY readOne(Class<ENTITY> aClass, Object identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <ENTITY> Collection<ENTITY> readMany(Class<ENTITY> aClass, Properties properties) {
		Collection<ENTITY> entities;
		ControllerEntity<ENTITY> controller = (ControllerEntity<ENTITY>)  __injectControllerLayer__().injectInterfaceClassFromEntityClass(aClass);
		if(controller == null){
			ControllerFunctionReader function = __inject__(ControllerFunctionReader.class);
			function.getProperties().copyFrom(properties, Properties.VALUE_USAGE_TYPE);
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
	
	/**/
	
	protected static ControllerLayer __injectControllerLayer__() {
		return __inject__(ControllerLayer.class);
	}

}
