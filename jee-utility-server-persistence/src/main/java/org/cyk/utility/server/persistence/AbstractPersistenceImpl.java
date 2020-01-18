package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassesGetter;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.__kernel__.value.ValueUsageType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractPersistenceImpl extends AbstractPersistenceServiceProviderImpl<Object> implements Persistence,Serializable {
	private static final long serialVersionUID = 1L;

	/* Create */
	
	@Override
	public PersistenceServiceProvider<Object> createMany(Collection<Object> objects, Properties properties) {
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects))) {
			PersistenceEntity<Object> persistence = __injectPersistenceLayer__().injectInterfaceClassFromEntity(objects.iterator().next());
			if(persistence == null){
				super.createMany(objects, properties);
			}else{
				persistence.createMany(objects, properties);
			}	
		}
		return this;
	}
	
	/* Read */
	
	
	@Override
	public <ENTITY> Collection<ENTITY> readByIdentifiers(Class<ENTITY> aClass, Collection<Object> identifiers,ValueUsageType valueUsageType, Properties properties) {
		if(aClass == null)
			throw new RuntimeException("class is required");
		if(Boolean.TRUE.equals(CollectionHelper.isEmpty(identifiers)))
			throw new RuntimeException("identifiers are required");
		PersistenceEntity<ENTITY> persistence = __injectPersistenceLayer__().injectInterfaceClassFromEntityClass(aClass);
		Collection<ENTITY> entities;
		if(persistence == null){
			PersistenceFunctionReader function = __inject__(PersistenceFunctionReader.class);
			function.setEntityClass(aClass);
			function.setEntityIdentifierValueUsageType(valueUsageType);
			function.getAction().getEntitiesIdentifiers(Boolean.TRUE).add(identifiers);
			__copyCommonProperties__(function, properties);
			function.execute();
			entities = (Collection<ENTITY>) function.getEntities();
		}else{
			entities = persistence.readByIdentifiers(identifiers, valueUsageType, properties);
		}
		return entities;
	}
	
	
	@Override
	public <ENTITY> Collection<ENTITY> readByIdentifiers(Class<ENTITY> aClass, Collection<Object> identifiers,ValueUsageType valueUsageType) {
		return readByIdentifiers(aClass, identifiers, valueUsageType, null);
	}
	
	
	@Override
	public <ENTITY> ENTITY readByIdentifier(Class<ENTITY> aClass, Object identifier,Properties properties) {
		if(aClass == null)
			throw new RuntimeException("class is required");
		if(identifier == null)
			throw new RuntimeException("identifier is required");
		ValueUsageType valueUsageType = properties == null ? ValueUsageType.SYSTEM : (ValueUsageType) ValueHelper.defaultToIfNull(properties.getValueUsageType(),ValueUsageType.SYSTEM);
		PersistenceEntity<ENTITY> persistence = __injectPersistenceLayer__().injectInterfaceClassFromEntityClass(aClass);
		ENTITY entity;
		if(persistence == null){
			entity = (ENTITY) __inject__(PersistenceFunctionReader.class).setEntityClass(aClass).setEntityIdentifier(identifier)
					.setEntityIdentifierValueUsageType(valueUsageType).execute().getProperties().getEntity();
		}else{
			entity = persistence.readByIdentifier(identifier,valueUsageType);
		}
		return entity;
	}
	
	@Override
	public <ENTITY> ENTITY readByIdentifier(Class<ENTITY> aClass, Object identifier) {
		return readByIdentifier(aClass, identifier, (Properties)null);
	}
	
	
	@Override
	public <ENTITY> ENTITY readByIdentifier(Class<ENTITY> aClass, Object identifier,ValueUsageType valueUsageType) {
		return readByIdentifier(aClass, identifier, new Properties().setValueUsageType(valueUsageType));
	}
	
	@Override
	public <ENTITY> Collection<ENTITY> read(Class<ENTITY> aClass, Properties properties) {
		PersistenceEntity<ENTITY> persistence = __injectPersistenceLayer__().injectInterfaceClassFromEntityClass(aClass);
		Collection<ENTITY> entities;
		if(persistence == null){
			entities = (Collection<ENTITY>) __inject__(PersistenceFunctionReader.class).setEntityClass(aClass).execute().getProperties().getEntities();
		}else{
			entities =  persistence.read(properties);
		}
		return entities;
	}
	
	@Override
	public <ENTITY> Collection<ENTITY> read(Class<ENTITY> aClass) {
		return read(aClass,null);
	}
	
	
	@Override
	public <ENTITY> Collection<Object> readIdentifiers(Class<ENTITY> aClass, ValueUsageType valueUsageType,Properties properties) {
		PersistenceEntity<ENTITY> persistence = __injectPersistenceLayer__().injectInterfaceClassFromEntityClass(aClass);
		Collection<Object> identifiers = null;
		if(persistence == null){
			ThrowableHelper.throwNotYetImplemented();
		}else{
			identifiers =  persistence.readIdentifiers(valueUsageType,properties);
		}
		return identifiers;
	}
	
	@Override
	public <ENTITY> Collection<Object> readIdentifiers(Class<ENTITY> aClass, ValueUsageType valueUsageType) {
		return readIdentifiers(aClass, valueUsageType, null);
	}
	
	
	@Override
	public <ENTITY> Long count(Class<ENTITY> aClass, Properties properties) {
		PersistenceEntity<ENTITY> persistence = __injectPersistenceLayer__().injectInterfaceClassFromEntityClass(aClass);
		Long count = null;
		if(persistence == null){
			ThrowableHelper.throwNotYetImplemented();
		}else{
			count =  persistence.count(properties);
		}
		return count;
	}
	
	@Override
	public <ENTITY> Long count(Class<ENTITY> aClass) {
		return count(aClass,null);
	}
	
	
	/* Update */
	
	@Override
	public PersistenceServiceProvider<Object> updateMany(Collection<Object> objects, Properties properties) {
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects))) {
			PersistenceEntity<Object> persistence = __injectPersistenceLayer__().injectInterfaceClassFromEntity(objects.iterator().next());
			if(persistence == null){
				super.updateMany(objects, properties);
			}else{
				persistence.updateMany(objects, properties);
			}	
		}
		return this;
	}
	
	/* Delete */
	
	@Override
	public PersistenceServiceProvider<Object> delete(Object object, Properties properties) {
		PersistenceEntity<Object> persistence = __injectPersistenceLayer__().injectInterfaceClassFromEntity(object);
		if(persistence == null){
			super.delete(object, properties);
		}else{
			persistence.delete(object, properties);
		}
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<Object> deleteMany(Collection<Object> objects, Properties properties) {
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects))) {
			PersistenceEntity<Object> persistence = __injectPersistenceLayer__().injectInterfaceClassFromEntity(objects.iterator().next());
			if(persistence == null){
				super.deleteMany(objects, properties);
			}else{
				persistence.deleteMany(objects, properties);
			}	
		}
		return this;
	}
	
	@Override
	public <ENTITY> Persistence deleteByIdentifiers(Class<ENTITY> aClass, Collection<Object> identifiers,ValueUsageType valueUsageType, Properties properties) {
		PersistenceEntity<ENTITY> persistence = __injectPersistenceLayer__().injectInterfaceClassFromEntityClass(aClass);
		if(persistence == null){
			ThrowableHelper.throwNotYetImplemented();
		}else{
			persistence.deleteByIdentifiers(identifiers, valueUsageType, properties);
		}
		return this;
	}
	
	@Override
	public <ENTITY> Persistence deleteByIdentifiers(Class<ENTITY> aClass, Collection<Object> identifiers,ValueUsageType valueUsageType) {
		return deleteByIdentifiers(aClass, identifiers, valueUsageType, null);
	}
		
	@Override
	public <ENTITY> Persistence deleteBySystemIdentifiers(Class<ENTITY> aClass, Collection<Object> identifiers,Properties properties) {
		deleteByIdentifiers(aClass, identifiers, ValueUsageType.SYSTEM, properties);
		return this;
	}
	
	@Override
	public <ENTITY> Persistence deleteBySystemIdentifiers(Class<ENTITY> aClass, Collection<Object> identifiers) {
		return deleteBySystemIdentifiers(aClass, identifiers, null);
	}

	@Override
	public <ENTITY> Persistence deleteByBusinessIdentifiers(Class<ENTITY> aClass, Collection<Object> identifiers,Properties properties) {
		deleteByIdentifiers(aClass, identifiers, ValueUsageType.BUSINESS, properties);
		return this;
	}
	
	@Override
	public <ENTITY> Persistence deleteByBusinessIdentifiers(Class<ENTITY> aClass, Collection<Object> identifiers) {
		return deleteByBusinessIdentifiers(aClass, identifiers, null);
	}
	
	@Override
	public <ENTITY> Persistence deleteByEntityClass(Class<ENTITY> aClass, Properties properties) {
		PersistenceEntity<ENTITY> persistence = __injectPersistenceLayer__().injectInterfaceClassFromEntityClass(aClass);
		if(persistence == null){
			ThrowableHelper.throwNotYetImplemented();
		}else{
			persistence.deleteAll();
		}
		return this;
	}
	
	@Override
	public <ENTITY> Persistence deleteByEntityClass(Class<ENTITY> aClass) {
		return deleteByEntityClass(aClass, null);
	}
	
	@Override
	public PersistenceServiceProvider<Object> deleteAll(Properties properties) {
		Collection<Class<?>> classes = ClassesGetter.getPersistable();
		if(Boolean.TRUE.equals(CollectionHelper.isEmpty(classes)))
			return this;
		for(Class<?> index : classes)
			deleteByEntityClass(index, properties);
		return this;
	}
	
	/**/
	
}
