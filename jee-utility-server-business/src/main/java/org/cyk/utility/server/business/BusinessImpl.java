package org.cyk.utility.server.business;

import java.io.Serializable;
import java.util.Collection;

import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.properties.Properties;

public class BusinessImpl extends AbstractBusinessServiceProviderImpl<Object> implements Business,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public BusinessServiceProvider<Object> create(Object object, Properties properties) {
		BusinessEntity<Object> business = (BusinessEntity<Object>)  __injectBusinessLayer__().injectInterfaceClassFromPersistenceEntity(object);
		if(business == null){
			super.create(object, properties);
		}else{
			business.create(object, properties);
		}
		return this;
	}
	
	@Override
	public BusinessServiceProvider<Object> createMany(Collection<Object> objects, Properties properties) {
		@SuppressWarnings("unchecked")
		Class<Object> aClass = (Class<Object>) objects.iterator().next().getClass();
		BusinessEntity<Object> business = (BusinessEntity<Object>)  __injectBusinessLayer__().injectInterfaceClassFromPersistenceEntityClass(aClass);
		if(business == null){
			super.createMany(objects, properties);
		}else{
			business.createMany(objects, properties);
		}
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> ENTITY findOne(Class<ENTITY> aClass, Object identifier, Properties properties) {
		ENTITY entity;
		BusinessEntity<ENTITY> business = (BusinessEntity<ENTITY>)  __injectBusinessLayer__().injectInterfaceClassFromPersistenceEntityClass(aClass);
		if(business == null){
			BusinessFunctionReader function = __inject__(BusinessFunctionReader.class);
			function.getProperties().copyFrom(properties, Properties.VALUE_USAGE_TYPE);
			entity = (ENTITY) function.setEntityClass(aClass).setEntityIdentifier(identifier).execute().getProperties().getEntity();
		}else{
			entity = business.findOne(identifier, properties);
		}
		return entity;
	}

	@Override
	public <ENTITY> ENTITY findOne(Class<ENTITY> aClass, Object identifier) {
		return findOne(aClass, identifier, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> Collection<ENTITY> findMany(Class<ENTITY> aClass, Properties properties) {
		Collection<ENTITY> entities;
		BusinessEntity<ENTITY> business = (BusinessEntity<ENTITY>)  __injectBusinessLayer__().injectInterfaceClassFromPersistenceEntityClass(aClass);
		if(business == null){
			BusinessFunctionReader function = __inject__(BusinessFunctionReader.class);
			function.getProperties().copyFrom(properties, Properties.VALUE_USAGE_TYPE);
			entities = (Collection<ENTITY>) function.setEntityClass(aClass).execute().getProperties().getEntities();
		}else{
			entities = business.findMany(properties);
		}
		return entities;
	}

	@Override
	public <ENTITY> Collection<ENTITY> findMany(Class<ENTITY> aClass) {
		//TODO handle pagination
		return findMany(aClass, null);
	}

	@Override
	public <ENTITY> Long count(Class<ENTITY> aClass, Properties properties) {
		Long value;
		BusinessEntity<ENTITY> business = (BusinessEntity<ENTITY>)  __injectBusinessLayer__().injectInterfaceClassFromPersistenceEntityClass(aClass);
		if(business == null){
			//BusinessFunctionReader function = __inject__(BusinessFunctionReader.class);
			//function.getProperties().copyFrom(properties, Properties.VALUE_USAGE_TYPE);
			//value = (Collection<ENTITY>) function.setEntityClass(aClass).execute().getProperties().getEntities();
			value = null;
		}else{
			value = business.count(properties);
		}
		return value;
	}

	@Override
	public <ENTITY> Long count(Class<ENTITY> aClass) {
		return count(aClass, null);
	}

	@Override @Transactional
	public Business deleteAll(Collection<Class<?>> classes) {
		if(__injectCollectionHelper__().isNotEmpty(classes)) {
			for(Class<?> index : classes) {
				@SuppressWarnings("unchecked")
				BusinessEntity<Object> business = (BusinessEntity<Object>)  __injectBusinessLayer__().injectInterfaceClassFromPersistenceEntityClass(index);
				if(business == null){
					//TODO to be implemented
				}else{
					business.deleteAll();
				}
			}
		}
		return this;
	}
	
	@Override @Transactional
	public Business deleteAll(Class<?>... classes) {
		return deleteAll(__injectCollectionHelper__().instanciate(classes));
	}
	
	@Override @Transactional
	public BusinessServiceProvider<Object> deleteAll() {
		// TODO Find class hierarchy and delete from leaf to root
		Collection<Class<?>> classes = null;
		return deleteAll(classes);
	}

	
}
