package org.cyk.utility.server.business;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.collection.CollectionInstance;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.array.ArrayInstanceTwoDimensionString;
import org.cyk.utility.clazz.ClassNameBuilder;
import org.cyk.utility.map.MapInstanceIntegerToString;
import org.cyk.utility.server.persistence.PersistenceEntity;
import org.cyk.utility.server.persistence.PersistenceLayer;

public abstract class AbstractBusinessEntityImpl<ENTITY,PERSISTENCE extends PersistenceEntity<ENTITY>> extends AbstractBusinessServiceProviderImpl<ENTITY> implements BusinessEntity<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	protected Class<ENTITY> __persistenceEntityClass__;
	protected PERSISTENCE __persistence__;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		if(__persistenceEntityClass__ == null) {
			ClassNameBuilder classNameBuilder = __inject__(ClassNameBuilder.class).setKlass(getClass());
			classNameBuilder.getSourceNamingModel(Boolean.TRUE).server().business().impl().suffix();
			classNameBuilder.getDestinationNamingModel(Boolean.TRUE).server().persistence().entities();
			__persistenceEntityClass__ = ValueHelper.returnOrThrowIfBlank("persistence entity class",(Class<ENTITY>) ClassHelper.getByName(classNameBuilder.execute().getOutput()));
		}
		if(__persistence__ == null)
			__persistence__ = (PERSISTENCE) ValueHelper.returnOrThrowIfBlank("persistence",__inject__(PersistenceLayer.class).injectInterfaceClassFromEntityClass(__persistenceEntityClass__));	
	}
	
	//TODO : an idea is to transform array content to json format and transform it java object
	@Override
	public BusinessEntity<ENTITY> saveFromArray(ArrayInstanceTwoDimensionString arrayInstanceTwoDimensionString,MapInstanceIntegerToString columnIndexFieldNameMap, Properties properties) {
		ValueHelper.throwIfBlank("save from array : array instance", arrayInstanceTwoDimensionString);
		Collection<ENTITY> entities = null;
		for(Integer index  = 0; index < arrayInstanceTwoDimensionString.getFirstDimensionElementCount(); index = index + 1) {
			ENTITY entitiy = ClassHelper.instanciate(__persistenceEntityClass__);
			for(Map.Entry<Integer, String> indexEntry : columnIndexFieldNameMap.getEntries()) {
				FieldHelper.write(entitiy, indexEntry.getValue(), arrayInstanceTwoDimensionString.get(index, indexEntry.getKey()));
			}
			if(entities == null)
				entities = new ArrayList<ENTITY>();
			entities.add(entitiy);
		}
		if(CollectionHelper.isNotEmpty(entities)) {
			__logInfo__("Saving "+entities.size()+" "+__persistenceEntityClass__.getSimpleName());
			saveMany(entities);		
		}
		return this;
	}
	
	/*@Override //TODO
	public BusinessEntity<ENTITY> saveFromFileExcelSheet(SheetReader sheetReader,MapInstanceIntegerToString columnIndexFieldNameMap, Properties properties) {
		//saveFromArray(fileExcelSheetDataArrayReader.execute().getOutput(), columnIndexFieldNameMap, properties);
		return this;
	}*/
	
	//TODO : an idea is to transform excel content to json format and transform it java object
	@Override
	public BusinessEntity<ENTITY> saveFromFileExcelSheet(InputStream workbookInputStream,String sheetName,MapInstanceIntegerToString columnIndexFieldNameMap,Properties properties) {
		/*
		ValueHelper.throwIfBlank("save many from file excel sheet : workbook input stream",workbookInputStream);
		ValueHelper.throwIfBlank("save many from file excel sheet : sheet name",sheetName);
		ValueHelper.throwIfBlank("create many from file excel sheet : column index and field name mapping",columnIndexFieldNameMap);
		
		FileExcelSheetDataArrayReader reader = __inject__(FileExcelSheetDataArrayReader.class);
		reader.setWorkbookInputStream(workbookInputStream).setSheetName(sheetName);
		reader.getRowInterval(Boolean.TRUE).getLow(Boolean.TRUE).setValue(1);
		
		saveFromFileExcelSheet(reader, columnIndexFieldNameMap, properties);
		*/
		return this;
	}
	
	@Override
	public Collection<ENTITY> findByIdentifiers(Collection<Object> identifiers, ValueUsageType valueUsageType,Properties properties) {
		BusinessFunctionReader function = __inject__(BusinessFunctionReader.class);
		function.setEntityClass(__persistenceEntityClass__);
		function.getAction().getEntitiesIdentifiers(Boolean.TRUE).add(identifiers);
		function.setEntityIdentifierValueUsageType(valueUsageType);
		__copyCommonProperties__(function, properties);
		@SuppressWarnings("unchecked")
		Collection<ENTITY> entities = (Collection<ENTITY>) function.execute().getEntities();
		__listenFindManyAfter__(entities, properties);
		return entities;
	}
	
	@Override
	public Collection<ENTITY> findByIdentifiers(Collection<Object> identifiers, ValueUsageType valueUsageType) {
		return findByIdentifiers(identifiers, valueUsageType, null);
	}
	
	@Override
	public Collection<ENTITY> findByIdentifiers(Collection<Object> identifiers) {
		return findByIdentifiers(identifiers, ValueUsageType.SYSTEM);
	}
	
	@Override
	public Collection<ENTITY> findBySystemIdentifiers(Collection<Object> identifiers, Properties properties) {
		return findByIdentifiers(identifiers, ValueUsageType.SYSTEM);
	}
	
	@Override
	public Collection<ENTITY> findBySystemIdentifiers(Collection<Object> identifiers) {
		return findBySystemIdentifiers(identifiers, null);
	}
	
	@Override
	public Collection<ENTITY> findByBusinessIdentifiers(Collection<Object> identifiers, Properties properties) {
		return findByIdentifiers(identifiers, ValueUsageType.BUSINESS);
	}
	
	@Override
	public Collection<ENTITY> findByBusinessIdentifiers(Collection<Object> identifiers) {
		return findByBusinessIdentifiers(identifiers, null);
	}
	
	@Override
	public ENTITY findByIdentifier(Object identifier,ValueUsageType valueUsageType, Properties properties) {
		//TODO add logic to confirm that only one result has been found otherwise throw an exception
		return CollectionHelper.getFirst(findByIdentifiers(Arrays.asList(identifier), valueUsageType, properties));
	}

	@Override
	public ENTITY findByIdentifier(Object identifier, ValueUsageType valueUsageType) {
		return findByIdentifier(identifier,valueUsageType,null);
	}

	@Override
	public ENTITY findByIdentifier(Object identifier) {
		return findByIdentifier(identifier,ValueUsageType.SYSTEM);
	}

	@Override
	public ENTITY findByBusinessIdentifier(Object identifier, Properties properties) {
		return findByIdentifier(identifier, ValueUsageType.BUSINESS,properties);
	}
	
	@Override
	public ENTITY findByBusinessIdentifier(Object identifier) {
		return findByBusinessIdentifier(identifier, null);
	}
	
	@Override
	public ENTITY findBySystemIdentifier(Object identifier, Properties properties) {
		return findByIdentifier(identifier, ValueUsageType.SYSTEM,properties);
	}
	
	@Override
	public ENTITY findBySystemIdentifier(Object identifier) {
		return findBySystemIdentifier(identifier, null);
	}

	@Override
	public Collection<ENTITY> find(Properties properties) {
		Collection<ENTITY> entities = __persistence__.read(properties);
		__listenFindManyAfter__(entities,properties);
		return entities;
	}

	@Override
	public Collection<ENTITY> find() {
		//TODO use default settings like pagination and sorting
		return find(null);
	}
	
	@Override
	public Collection<Object> findIdentifiers(ValueUsageType valueUsageType, Properties properties) {
		return __persistence__.readIdentifiers(valueUsageType, properties);
	}
	
	@Override
	public Collection<Object> findIdentifiers(ValueUsageType valueUsageType) {
		return findIdentifiers(valueUsageType, null);
	}
	
	@Override
	public Collection<Object> findSystemIdentifiers(Properties properties) {
		return findIdentifiers(ValueUsageType.SYSTEM, properties);
	}
	
	@Override
	public Collection<Object> findSystemIdentifiers() {
		return findSystemIdentifiers(null);
	}
	
	@Override
	public Collection<Object> findBusinessIdentifiers(Properties properties) {
		return findIdentifiers(ValueUsageType.BUSINESS, properties);
	}
	
	@Override
	public Collection<Object> findBusinessIdentifiers() {
		return findBusinessIdentifiers(null);
	}

	protected void __listenFindManyAfter__(Collection<ENTITY> entities,Properties properties) {
		if(CollectionHelper.isNotEmpty(entities)) {
			for(ENTITY index : entities)
				__processAfterRead__(index,properties);	
		}
	}
	
	protected void __listenFindOneAfter__(ENTITY entity,Properties properties) {
		if(entity != null)
			__processAfterRead__(entity, properties);
	}
	
	protected void __processAfterRead__(ENTITY entity,Properties properties) {
		
	}
	
	@Override
	public Long count(Properties properties) {
		return __persistence__.count(properties);
	}

	@Override
	public Long count() {
		//TODO use default settings like pagination and sorting
		return count(null);
	}
	
	/* Delete */
	
	protected Boolean __isCallDeleteByInstanceOnDeleteByIdentifier__() {
		return Boolean.FALSE;
	}
	
	@Override @Transactional
	public BusinessEntity<ENTITY> deleteByIdentifiers(Collection<Object> identifiers, ValueUsageType valueUsageType,Properties properties) {
		if(Boolean.TRUE.equals(__isCallDeleteByInstanceOnDeleteByIdentifier__())) {
			Collection<ENTITY> entities = __persistence__.readByIdentifiers(identifiers, valueUsageType, properties);
			if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(entities))) {
				deleteMany(entities, properties);
			}
		}else {
			BusinessFunctionRemover function = __inject__(BusinessFunctionRemover.class);
			function.getAction().getEntitiesIdentifiers(Boolean.TRUE).add(identifiers);
			function.setEntityClass(__persistenceEntityClass__);
			function.setEntityIdentifierValueUsageType(valueUsageType);
			__copyCommonProperties__(function, properties);
			function.execute();	
		}
		return this;
	}
	
	@Override @Transactional
	public BusinessEntity<ENTITY> deleteByIdentifiers(Collection<Object> identifiers,ValueUsageType valueUsageType) {
		deleteByIdentifiers(identifiers, valueUsageType, null);
		return this;
	}
	
	@Override @Transactional
	public BusinessEntity<ENTITY> deleteByIdentifier(Object identifier,ValueUsageType valueUsageType,Properties properties) {
		deleteByIdentifiers(Arrays.asList(identifier), valueUsageType, properties);
		return this;
	}
	
	@Override
	public BusinessEntity<ENTITY> deleteByIdentifier(Object identifier, ValueUsageType valueUsageType) {
		return deleteByIdentifier(identifier, valueUsageType, null);
	}
	
	@Override @Transactional
	public BusinessEntity<ENTITY> deleteBySystemIdentifier(Object identifier,Properties properties) {
		deleteByIdentifier(identifier,ValueUsageType.SYSTEM,properties);
		return this;
	}
	
	@Override
	public BusinessEntity<ENTITY> deleteBySystemIdentifier(Object identifier) {
		return deleteBySystemIdentifier(identifier, null);
	}
	
	@Override @Transactional
	public BusinessEntity<ENTITY> deleteByBusinessIdentifier(Object identifier,Properties properties) {
		deleteByIdentifier(identifier,ValueUsageType.BUSINESS,properties);
		return this;
	}
	
	@Override
	public BusinessEntity<ENTITY> deleteByBusinessIdentifier(Object identifier) {
		return deleteByBusinessIdentifier(identifier, null);
	}
	
	@Override @Transactional
	public BusinessEntity<ENTITY> deleteBySystemIdentifiers(Collection<Object> identifiers, Properties properties) {
		return deleteByIdentifiers(identifiers, ValueUsageType.SYSTEM, properties);
	}
	
	@Override @Transactional
	public BusinessEntity<ENTITY> deleteBySystemIdentifiers(Collection<Object> identifiers) {
		return deleteBySystemIdentifiers(identifiers, null);
	}
	
	@Override @Transactional
	public BusinessEntity<ENTITY> deleteByBusinessIdentifiers(Collection<Object> identifiers,Properties properties) {
		return deleteByIdentifiers(identifiers, ValueUsageType.BUSINESS, properties);
	}
	
	@Override @Transactional
	public BusinessEntity<ENTITY> deleteByBusinessIdentifiers(Collection<Object> identifiers) {
		return deleteByBusinessIdentifiers(identifiers, null);
	}
	
	@Override @Transactional
	public BusinessEntity<ENTITY> deleteAll(Properties properties) {
		Collection<ENTITY> entities = find();
		if(CollectionHelper.isNotEmpty(entities))
			deleteMany(entities,properties);
		return this;
	}
	
	@Override @Transactional
	public BusinessEntity<ENTITY> deleteAll() {
		deleteAll(null);
		return this;
	}
	
	/* Import */
	
	@Override
	public BusinessEntity<ENTITY> import_(Properties properties) {
		ThrowableHelper.throwNotYetImplemented();
		return this;
	}
	
	
	/**/
	
	protected static void __create__(Object object) {
		if(object != null)
			__inject__(Business.class).create(object);
	}
	
	@SuppressWarnings("unchecked")
	protected static void __createMany__(Collection<?> objects) {
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects)))
			__inject__(Business.class).createMany((Collection<Object>) objects);
	}
	
	protected static void __createIfSystemIdentifierIsBlank__(Object object) {
		if(object != null)
			__inject__(Business.class).create(object,new Properties().setIsCreateIfSystemIdentifierIsBlank(Boolean.TRUE));	
	}
	
	protected static void __update__(Object object) {
		if(object != null)
			__inject__(Business.class).update(object);
	}
	
	@SuppressWarnings("unchecked")
	protected static void __updateMany__(Collection<?> objects) {
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects)))
			__inject__(Business.class).updateMany((Collection<Object>) objects);
	}
	
	protected static void __save__(Object object) {
		if(object != null)
			__inject__(Business.class).save(object);
	}
	
	@SuppressWarnings("unchecked")
	protected static void __saveMany__(Collection<?> objects) {
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects)))
			__inject__(Business.class).saveMany((Collection<Object>) objects);
	}
	
	protected static void __delete__(Object object) {
		if(object != null)
			__inject__(Business.class).delete(object);
	}
	
	@SuppressWarnings("unchecked")
	protected static void __deleteMany__(Collection<?> objects) {
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects)))
			__inject__(Business.class).deleteMany((Collection<Object>) objects);
	}
	
	protected <T> Collection<T> __getDeletableInstances__(Collection<?> finalInstances,Collection<T> persistedInstances,String fieldName) {
		Collection<T> collection = null;
		if(CollectionHelper.isNotEmpty(persistedInstances))
			for(T index : persistedInstances) {
				if(!Boolean.TRUE.equals(CollectionHelper.contains(finalInstances, FieldHelper.read(index, fieldName)))) {
					if(collection == null)
						collection = new ArrayList<>();
					collection.add(index);
				}
			}
		return collection;
	}
	
	protected <T> Collection<T> __getDeletableInstances__(CollectionInstance<?> finalInstances,Collection<T> persistedInstances,String fieldName) {
		return __getDeletableInstances__(finalInstances == null ? null : finalInstances.get(), persistedInstances, fieldName);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> void __delete__(Collection<?> finalInstances,Collection<T> persistedInstances,String fieldName) {
		Collection<T> collection = __getDeletableInstances__(finalInstances, persistedInstances, fieldName);
		if(CollectionHelper.isEmpty(collection))
			return;
		__inject__(Business.class).deleteMany((Collection<Object>) collection);
	}
	
	protected <T> void __delete__(CollectionInstance<?> finalInstances,Collection<T> persistedInstances,String fieldName) {
		__delete__(finalInstances == null ? null : finalInstances.get() , persistedInstances, fieldName);
	}
	
	protected <M,D> Boolean __isSavableInstance__(M finalInstance,Collection<M> persistedInstances) {
		//check if not yet created
		//return CollectionHelper.contains(persistedInstances, finalInstance);
		return Boolean.TRUE;
	}
	
	protected <M,D> D __getSavableInstance__(Class<D> klass,M finalInstance,Collection<M> persistedInstances,String fieldName,Object master,String masterFieldName) {
		if(!__isSavableInstance__(finalInstance, persistedInstances))
			return null;
		D instance = ClassHelper.instanciate(klass);
		FieldHelper.write(instance, masterFieldName, master);
		FieldHelper.write(instance, fieldName, finalInstance);	
		return instance;
	}
	
	protected <M,D> Collection<D> __getSavableInstances__(Class<D> klass,Collection<M> finalInstances,Collection<M> persistedInstances,String fieldName,Object master,String masterFieldName) {
		Collection<D> collection = null;
		if(CollectionHelper.isNotEmpty(finalInstances)) {
			for(M index : finalInstances) {
				D instance = __getSavableInstance__(klass, index, persistedInstances, fieldName, master, masterFieldName);
				if(instance == null)
					continue;
				if(collection == null)
					collection = new ArrayList<>();
				collection.add(instance);	
				
				/*//check if not yet created
				if(!Boolean.TRUE.equals(CollectionHelper.contains(persistedInstances, index))) {
					if(collection == null)
						collection = new ArrayList<>();
					D instance = ClassHelper.instanciate(klass);
					FieldHelper.write(instance, masterFieldName, master);
					FieldHelper.write(instance, fieldName, index);
					collection.add(instance);	
				}*/
			}
		}
		return collection;
	}
	
	protected <M,D> Collection<D> __getSavableInstances__(Class<D> klass,CollectionInstance<M> finalInstances,Collection<M> persistedInstances,String fieldName,Object master,String masterFieldName) {
		return __getSavableInstances__(klass, finalInstances == null ? null : finalInstances.get(), persistedInstances, fieldName, master, masterFieldName);
	}
	
	@SuppressWarnings("unchecked")
	protected <M,D> void __save__(Class<D> klass,Collection<M> finalInstances,Collection<M> persistedInstances,String fieldName,Object master,String masterFieldName) {
		Collection<D> collection = __getSavableInstances__(klass, finalInstances, persistedInstances, fieldName, master, masterFieldName);
		if(CollectionHelper.isEmpty(collection))
			return;
		__inject__(Business.class).saveMany((Collection<Object>) collection);
	}
	
	protected <M,D> void __save__(Class<D> klass,CollectionInstance<M> finalInstances,Collection<M> persistedInstances,String fieldName,Object master,String masterFieldName) {
		__save__(klass, finalInstances == null ? null : finalInstances.get(), persistedInstances, fieldName, master, masterFieldName);
	}
	
}
