package org.cyk.utility.server.business;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.server.persistence.Persistence;

@ApplicationScoped
public class BusinessImpl extends AbstractBusinessServiceProviderImpl<Object> implements Business,Serializable {
	private static final long serialVersionUID = 1L;

	/* Create */
	
	@Override
	public BusinessServiceProvider<Object> createMany(Collection<Object> objects, Properties properties) {
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects))) {
			@SuppressWarnings("unchecked")
			Class<Object> aClass = (Class<Object>) objects.iterator().next().getClass();
			BusinessEntity<Object> business = (BusinessEntity<Object>)  __injectBusinessLayer__().injectInterfaceClassFromPersistenceEntityClass(aClass);
			if(business == null){
				super.createMany(objects, properties);
			}else{
				business.createMany(objects, properties);
			}
		}
		return this;
	}
	
	/* Find */
	
	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> Collection<ENTITY> findByIdentifiers(Class<ENTITY> aClass, Collection<Object> identifiers,ValueUsageType valueUsageType, Properties properties) {
		Collection<Object> entities = null;
		BusinessEntity<Object> business = (BusinessEntity<Object>)  __injectBusinessLayer__().injectInterfaceClassFromPersistenceEntityClass(aClass);
		if(business == null){
			ThrowableHelper.throwNotYetImplemented();
		}else{
			entities = business.findByIdentifiers(identifiers, valueUsageType, properties);
		}
		return (Collection<ENTITY>) entities;
	}
	
	@Override
	public <ENTITY> Collection<ENTITY> findByIdentifiers(Class<ENTITY> aClass, Collection<Object> identifiers,ValueUsageType valueUsageType) {
		return findByIdentifiers(aClass, identifiers, valueUsageType, null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> ENTITY findByIdentifier(Class<ENTITY> aClass, Object identifier,ValueUsageType valueUsageType, Properties properties) {
		ENTITY entity;
		BusinessEntity<ENTITY> business = (BusinessEntity<ENTITY>)  __injectBusinessLayer__().injectInterfaceClassFromPersistenceEntityClass(aClass);
		if(business == null){
			BusinessFunctionReader function = __inject__(BusinessFunctionReader.class);
			function.getProperties().copyFrom(properties, Properties.VALUE_USAGE_TYPE);
			entity = (ENTITY) function.setEntityClass(aClass).setEntityIdentifier(identifier).execute().getProperties().getEntity();
		}else{
			entity = business.findByIdentifier(identifier,valueUsageType, properties);
		}
		return entity;
	}

	@Override
	public <ENTITY> ENTITY findByIdentifier(Class<ENTITY> aClass, Object identifier,ValueUsageType valueUsageType) {
		return findByIdentifier(aClass, identifier,valueUsageType, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> Collection<ENTITY> find(Class<ENTITY> aClass, Properties properties) {
		Collection<ENTITY> entities;
		BusinessEntity<ENTITY> business = (BusinessEntity<ENTITY>)  __injectBusinessLayer__().injectInterfaceClassFromPersistenceEntityClass(aClass);
		if(business == null){
			BusinessFunctionReader function = __inject__(BusinessFunctionReader.class);
			function.getProperties().copyFrom(properties, Properties.VALUE_USAGE_TYPE);
			entities = (Collection<ENTITY>) function.setEntityClass(aClass).execute().getProperties().getEntities();
		}else{
			entities = business.find(properties);
		}
		return entities;
	}

	@Override
	public <ENTITY> Collection<ENTITY> find(Class<ENTITY> aClass) {
		//TODO handle pagination
		return find(aClass, null);
	}
	
	@Override
	public <ENTITY> Collection<Object> findIdentifiers(Class<ENTITY> aClass, ValueUsageType valueUsageType,Properties properties) {
		Collection<Object> identifiers = null;
		BusinessEntity<ENTITY> business = (BusinessEntity<ENTITY>)  __injectBusinessLayer__().injectInterfaceClassFromPersistenceEntityClass(aClass);
		if(business == null){
			ThrowableHelper.throwNotYetImplemented();
		}else{
			identifiers = business.findIdentifiers(valueUsageType, properties);
		}
		return identifiers;
	}
	
	@Override
	public <ENTITY> Collection<Object> findIdentifiers(Class<ENTITY> aClass, ValueUsageType valueUsageType) {
		return findIdentifiers(aClass, valueUsageType, null);
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

	/* Update */
	
	@Override
	public BusinessServiceProvider<Object> updateMany(Collection<Object> objects, Properties properties) {
		@SuppressWarnings("unchecked")
		Class<Object> aClass = (Class<Object>) objects.iterator().next().getClass();
		BusinessEntity<Object> business = (BusinessEntity<Object>)  __injectBusinessLayer__().injectInterfaceClassFromPersistenceEntityClass(aClass);
		if(business == null){
			super.updateMany(objects, properties);
		}else{
			business.updateMany(objects, properties);
		}
		return this;
	}
	
	/* Delete */
	
	@Override
	public BusinessServiceProvider<Object> deleteMany(Collection<Object> objects, Properties properties) {
		BusinessEntity<Object> business = (BusinessEntity<Object>)  __injectBusinessLayer__().injectInterfaceClassFromPersistenceEntity(CollectionHelper.isEmpty(objects) ? null : objects.iterator().next());
		if(business == null){
			super.deleteMany(objects, properties);
		}else{
			business.deleteMany(objects, properties);
		}
		return this;
	}

	@Override @Transactional
	public Business deleteByClasses(Collection<Class<?>> classes) {
		if(CollectionHelper.isNotEmpty(classes)) {
			for(Class<?> index : classes) {
				@SuppressWarnings("unchecked")
				BusinessEntity<Object> business = (BusinessEntity<Object>)  __injectBusinessLayer__().injectInterfaceClassFromPersistenceEntityClass(index);
				if(business == null){
					ThrowableHelper.throwNotYetImplemented();
				}else{
					business.deleteAll();
				}
			}
		}
		return this;
	}
	
	@Override @Transactional
	public Business deleteByClasses(Class<?>... classes) {
		if(__inject__(ArrayHelper.class).isNotEmpty(classes))
			deleteByClasses(List.of(classes));
		return this;
	}
	
	@Override @Transactional
	public Business deleteAll(Properties properties) {
		__inject__(Persistence.class).deleteAll();
		return this;
	}
	
	@Override @Transactional
	public Business deleteAll() {
		return deleteAll(null);
	}
	
	@Override @Transactional
	public Business deleteByIdentifiers(Class<?> klass, Collection<Object> identifiers, ValueUsageType valueUsageType,Properties properties) {
		@SuppressWarnings("unchecked")
		BusinessEntity<Object> business = (BusinessEntity<Object>)  __injectBusinessLayer__().injectInterfaceClassFromPersistenceEntityClass(klass);
		if(business == null){
			ThrowableHelper.throwNotYetImplemented();
		}else{
			business.deleteByIdentifiers(identifiers, valueUsageType,properties);
		}
		return this;
	}
	
	@Override @Transactional
	public Business deleteByIdentifiers(Class<?> klass, Collection<Object> identifiers, ValueUsageType valueUsageType) {
		return deleteByIdentifiers(klass, identifiers, valueUsageType, null);
	}
	
	@Override @Transactional
	public Business deleteBySystemIdentifiers(Class<?> klass, Collection<Object> identifiers, Properties properties) {
		return deleteByIdentifiers(klass, identifiers,ValueUsageType.SYSTEM, properties);
	}
	
	@Override @Transactional
	public Business deleteBySystemIdentifiers(Class<?> klass, Collection<Object> identifiers) {
		return deleteBySystemIdentifiers(klass, identifiers, null);
	}
	
	@Override @Transactional
	public Business deleteByBusinessIdentifiers(Class<?> klass, Collection<Object> identifiers, Properties properties) {
		return deleteByIdentifiers(klass, identifiers,ValueUsageType.BUSINESS, properties);
	}
	
	@Override @Transactional
	public Business deleteByBusinessIdentifiers(Class<?> klass, Collection<Object> identifiers) {
		return deleteByBusinessIdentifiers(klass, identifiers, null);
	}
	
	/* Save */
	
	@Override
	public BusinessServiceProvider<Object> saveByBatch(Collection<Object> objects, Object batchSize,
			Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public BusinessServiceProvider<Object> saveByBatch(Collection<Object> objects, Object batchSize) {
		// TODO Auto-generated method stub
		return null;
	}
}
