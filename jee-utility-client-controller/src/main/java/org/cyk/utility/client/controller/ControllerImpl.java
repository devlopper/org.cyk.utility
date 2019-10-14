package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.identifier.resource.ProxyHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.server.representation.Representation;

@ApplicationScoped
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
	
	/* Delete */
	
	@Override
	public <ENTITY> Collection<ENTITY> read(Class<ENTITY> aClass, Properties properties) {
		Collection<ENTITY> entities;
		ControllerEntity<ENTITY> controller = (ControllerEntity<ENTITY>)  __injectControllerLayer__().injectInterfaceClassFromEntityClass(aClass);
		if(controller == null){
			ControllerFunctionReader function = __inject__(ControllerFunctionReader.class);
			__copyReadProperties__(function, properties);
			entities = (Collection<ENTITY>) function.setActionEntityClass(aClass).execute().getProperties().getEntities();
		}else{
			entities = controller.read(properties);
		}
		return entities;
	}

	@Override
	public <ENTITY> Collection<ENTITY> read(Class<ENTITY> aClass) {
		return read(aClass, null);
	}
	
	@Override
	public <ENTITY> Collection<ENTITY> readByIdentifiers(Class<ENTITY> aClass, Collection<Object> identifiers,ValueUsageType valueUsageType, Properties properties) {
		Collection<ENTITY> entities = null;
		ControllerEntity<ENTITY> controller = (ControllerEntity<ENTITY>)  __injectControllerLayer__().injectInterfaceClassFromEntityClass(aClass);
		if(controller == null){
			ThrowableHelper.throwNotYetImplemented();
		}else{
			entities = controller.readByIdentifiers(identifiers, valueUsageType, properties);
		}
		return entities;
	}
	
	@Override
	public <ENTITY> Collection<ENTITY> readByIdentifiers(Class<ENTITY> aClass, Collection<Object> identifiers,ValueUsageType valueUsageType) {
		return readByIdentifiers(aClass, identifiers, valueUsageType, null);
	}
	
	@Override
	public <ENTITY> Collection<ENTITY> readBySystemIdentifiers(Class<ENTITY> aClass, Collection<Object> identifiers,Properties properties) {
		return readByIdentifiers(aClass, identifiers, ValueUsageType.SYSTEM, properties);
	}
	
	@Override
	public <ENTITY> Collection<ENTITY> readBySystemIdentifiers(Class<ENTITY> aClass, Collection<Object> identifiers) {
		return readBySystemIdentifiers(aClass, identifiers,null);
	}
	
	@Override
	public <ENTITY> Collection<ENTITY> readByBusinessIdentifiers(Class<ENTITY> aClass, Collection<Object> identifiers,Properties properties) {
		return readByIdentifiers(aClass, identifiers, ValueUsageType.BUSINESS, properties);
	}
	
	@Override
	public <ENTITY> Collection<ENTITY> readByBusinessIdentifiers(Class<ENTITY> aClass, Collection<Object> identifiers) {
		return readByBusinessIdentifiers(aClass, identifiers,null);
	}
	
	@Override
	public <ENTITY> ENTITY readByIdentifier(Class<ENTITY> aClass, Object identifier, ValueUsageType valueUsageType,Properties properties) {
		ENTITY entity = null;
		ControllerEntity<ENTITY> controller = (ControllerEntity<ENTITY>)  __injectControllerLayer__().injectInterfaceClassFromEntityClass(aClass);
		if(controller == null){
			ThrowableHelper.throwNotYetImplemented();
		}else{
			entity = controller.readByIdentifier(identifier, valueUsageType, properties);
		}
		return entity;
	}
	
	@Override
	public <ENTITY> ENTITY readByIdentifier(Class<ENTITY> aClass, Object identifier, ValueUsageType valueUsageType) {
		return readByIdentifier(aClass, identifier, valueUsageType, null);
	}
	
	@Override
	public <ENTITY> ENTITY readBySystemIdentifier(Class<ENTITY> aClass, Object identifier, Properties properties) {
		return readByIdentifier(aClass, identifier, ValueUsageType.SYSTEM, properties);
	}
	
	@Override
	public <ENTITY> ENTITY readBySystemIdentifier(Class<ENTITY> aClass, Object identifier) {
		return readBySystemIdentifier(aClass, identifier, null);
	}
	
	@Override
	public <ENTITY> ENTITY readByBusinessIdentifier(Class<ENTITY> aClass, Object identifier, Properties properties) {
		return readByIdentifier(aClass, identifier, ValueUsageType.BUSINESS, properties);
	}
	
	@Override
	public <ENTITY> ENTITY readByBusinessIdentifier(Class<ENTITY> aClass, Object identifier) {
		return readByBusinessIdentifier(aClass, identifier, null);
	}
	
	@Override
	public <ENTITY> Long count(Class<ENTITY> aClass, Properties properties) {
		Long count = null;
		ControllerEntity<ENTITY> controller = (ControllerEntity<ENTITY>)  __injectControllerLayer__().injectInterfaceClassFromEntityClass(aClass);
		if(controller == null){
			ControllerFunctionCounter function = __inject__(ControllerFunctionCounter.class);
			__copyCountProperties__(function, properties);
			count = (Long) function.setActionEntityClass(aClass).execute().getProperties().getCount();
		}else{
			count = controller.count(properties);
		}
		return count;
	}

	@Override
	public <ENTITY> Long count(Class<ENTITY> aClass) {
		return count(aClass, null);
	}
	
	/* Delete */
	
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
	public Controller deleteAll(Properties properties) {
		Representation representation =  (Representation) ProxyHelper.get(Representation.class);
		representation.deleteAll();
		return this;
	}

	@Override
	public <ENTITY> Controller deleteByClassByIdentififerByValueUsageType(Class<ENTITY> clazz, Object identifier,
			ValueUsageType valueUsageType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <ENTITY> Controller select(Class<ENTITY> aClass, Collection<Object> identifiers, Properties properties) {
		ControllerEntity<Object> controller = (ControllerEntity<Object>)  __injectControllerLayer__().injectInterfaceClassFromEntityClass(aClass);
		if(controller == null){
			throw new RuntimeException("No controller found for <<"+aClass+">> to do select action");
		}else{
			controller.select(identifiers, properties);
		}
		return this;
	}
	
	@Override
	public <ENTITY> Controller select(Class<ENTITY> aClass, Collection<Object> identifiers) {
		return select(aClass, identifiers, null);
	}
	
	@Override
	public <ENTITY> Controller redirect(Class<ENTITY> aClass, Object identifier, Properties properties) {
		ControllerEntity<Object> controller = (ControllerEntity<Object>)  __injectControllerLayer__().injectInterfaceClassFromEntityClass(aClass);
		if(controller == null){
			throw new RuntimeException("No controller found for <<"+aClass+">> to do redirect action");
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
	
	@Override
	public ControllerServiceProvider<Object> process(Object object, Properties properties) {
		ControllerEntity<Object> controller = (ControllerEntity<Object>)  __injectControllerLayer__().injectInterfaceClassFromEntity(object);
		if(controller == null){
			super.process(object, properties);
		}else{
			controller.process(object, properties);
		}
		return this;
	}
	
	@Override
	public ControllerServiceProvider<Object> process(Object object) {
		return process(object,null);
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
