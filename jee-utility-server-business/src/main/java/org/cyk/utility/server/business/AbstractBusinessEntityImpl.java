package org.cyk.utility.server.business;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.array.ArrayInstanceTwoDimensionString;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.collection.CollectionInstance;
import org.cyk.utility.file.excel.FileExcelSheetDataArrayReader;
import org.cyk.utility.map.MapInstanceIntegerToString;
import org.cyk.utility.server.persistence.PersistenceEntity;
import org.cyk.utility.server.persistence.PersistenceLayer;
import org.cyk.utility.value.ValueUsageType;

import lombok.Getter;

public abstract class AbstractBusinessEntityImpl<ENTITY,PERSISTENCE extends PersistenceEntity<ENTITY>> extends AbstractBusinessServiceProviderImpl<ENTITY> implements BusinessEntity<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	protected Class<ENTITY> __persistenceEntityClass__;
	@Getter private PERSISTENCE persistence;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		if(__persistenceEntityClass__ == null) {
			String name = StringUtils.substringBefore(getClass().getName() , "BusinessImpl");
			name = StringUtils.replaceOnce(name, ".business.", ".persistence.");
			name = StringUtils.replaceOnce(name, ".impl.", ".entities.");
			__persistenceEntityClass__ = (Class<ENTITY>) __injectClassHelper__().getByName(name);
		}
		if(__persistenceEntityClass__ == null) {
			System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+getClass()+" : persistence entity class cannot be derived <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}else {
			persistence = (PERSISTENCE) __inject__(PersistenceLayer.class).injectInterfaceClassFromEntityClass(__persistenceEntityClass__);	
		}
	}
	
	//TODO : an idea is to transform array content to json format and transform it java object
	@Override
	public BusinessEntity<ENTITY> saveFromArray(ArrayInstanceTwoDimensionString arrayInstanceTwoDimensionString,MapInstanceIntegerToString columnIndexFieldNameMap, Properties properties) {
		__throwRuntimeExceptionIfEmpty__(arrayInstanceTwoDimensionString, "save from array : array instance");
		Collection<ENTITY> entities = null;
		for(Integer index  = 0; index < arrayInstanceTwoDimensionString.getFirstDimensionElementCount(); index = index + 1) {
			ENTITY entitiy = __injectClassHelper__().instanciate(__persistenceEntityClass__);
			for(Map.Entry<Integer, String> indexEntry : columnIndexFieldNameMap.getEntries()) {
				__injectFieldValueSetter__().execute(entitiy, indexEntry.getValue(), arrayInstanceTwoDimensionString.get(index, indexEntry.getKey()));
			}
			if(entities == null)
				entities = new ArrayList<ENTITY>();
			entities.add(entitiy);
		}
		if(__injectCollectionHelper__().isNotEmpty(entities)) {
			__logInfo__("Saving "+entities.size()+" "+__persistenceEntityClass__.getSimpleName());
			saveMany(entities);		
		}
		return this;
	}
	
	@Override
	public BusinessEntity<ENTITY> saveFromFileExcelSheet(FileExcelSheetDataArrayReader fileExcelSheetDataArrayReader,MapInstanceIntegerToString columnIndexFieldNameMap, Properties properties) {
		saveFromArray(fileExcelSheetDataArrayReader.execute().getOutput(), columnIndexFieldNameMap, properties);
		return this;
	}
	
	//TODO : an idea is to transform excel content to json format and transform it java object
	@Override
	public BusinessEntity<ENTITY> saveFromFileExcelSheet(InputStream workbookInputStream,String sheetName,MapInstanceIntegerToString columnIndexFieldNameMap,Properties properties) {
		__throwRuntimeExceptionIfBlank__(workbookInputStream, "save many from file excel sheet : workbook input stream");
		__throwRuntimeExceptionIfBlank__(sheetName, "save many from file excel sheet : sheet name");
		__throwRuntimeExceptionIfEmpty__(columnIndexFieldNameMap, "create many from file excel sheet : column index and field name mapping");
		
		FileExcelSheetDataArrayReader reader = __inject__(FileExcelSheetDataArrayReader.class);
		reader.setWorkbookInputStream(workbookInputStream).setSheetName(sheetName);
		reader.getRowInterval(Boolean.TRUE).getLow(Boolean.TRUE).setValue(1);
		
		saveFromFileExcelSheet(reader, columnIndexFieldNameMap, properties);		
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
		return __injectCollectionHelper__().getFirst(findByIdentifiers(Arrays.asList(identifier), valueUsageType, properties));
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
		Collection<ENTITY> entities = getPersistence().read(properties);
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
		return getPersistence().readIdentifiers(valueUsageType, properties);
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
		if(__injectCollectionHelper__().isNotEmpty(entities)) {
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
		return getPersistence().count(properties);
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
			Collection<ENTITY> entities = getPersistence().readByIdentifiers(identifiers, valueUsageType, properties);
			if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(entities))) {
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
		if(__injectCollectionHelper__().isNotEmpty(entities))
			deleteMany(entities,properties);
		return this;
	}
	
	@Override @Transactional
	public BusinessEntity<ENTITY> deleteAll() {
		deleteAll(null);
		return this;
	}
	
	/**/
	
	protected static void __create__(Object object) {
		if(object != null)
			__inject__(Business.class).create(object);
	}
	
	@SuppressWarnings("unchecked")
	protected static void __createMany__(Collection<?> objects) {
		if(Boolean.TRUE.equals(__inject__(CollectionHelper.class).isNotEmpty(objects)))
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
		if(Boolean.TRUE.equals(__inject__(CollectionHelper.class).isNotEmpty(objects)))
			__inject__(Business.class).updateMany((Collection<Object>) objects);
	}
	
	protected static void __save__(Object object) {
		if(object != null)
			__inject__(Business.class).save(object);
	}
	
	@SuppressWarnings("unchecked")
	protected static void __saveMany__(Collection<?> objects) {
		if(Boolean.TRUE.equals(__inject__(CollectionHelper.class).isNotEmpty(objects)))
			__inject__(Business.class).saveMany((Collection<Object>) objects);
	}
	
	protected static void __delete__(Object object) {
		if(object != null)
			__inject__(Business.class).delete(object);
	}
	
	@SuppressWarnings("unchecked")
	protected static void __deleteMany__(Collection<?> objects) {
		if(Boolean.TRUE.equals(__inject__(CollectionHelper.class).isNotEmpty(objects)))
			__inject__(Business.class).deleteMany((Collection<Object>) objects);
	}
	
	protected <T> Collection<T> __getDeletableInstances__(CollectionInstance<?> finalInstances,Collection<T> persistedInstances,String fieldName) {
		Collection<T> collection = null;
		if(__injectCollectionHelper__().isNotEmpty(persistedInstances))
			for(T index : persistedInstances) {
				if(!Boolean.TRUE.equals(__injectCollectionHelper__().contains(finalInstances, __injectFieldValueGetter__().execute(index, fieldName).getOutput()))) {
					if(collection == null)
						collection = new ArrayList<>();
					collection.add(index);
				}
			}
		return collection;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> void __delete__(CollectionInstance<?> finalInstances,Collection<T> persistedInstances,String fieldName) {
		Collection<T> collection = __getDeletableInstances__(finalInstances, persistedInstances, fieldName);
		if(__injectCollectionHelper__().isNotEmpty(collection))
			__inject__(Business.class).deleteMany((Collection<Object>) collection);
	}
	
	protected <M,D> Collection<D> __getSavableInstances__(Class<D> klass,CollectionInstance<M> finalInstances,Collection<M> persistedInstances,String fieldName,Object master,String masterFieldName) {
		Collection<D> collection = null;
		if(__injectCollectionHelper__().isNotEmpty(finalInstances)) {
			for(Object index : finalInstances.get()) {
				//check if not yet created
				if(!Boolean.TRUE.equals(__injectCollectionHelper__().contains(persistedInstances, index))) {
					if(collection == null)
						collection = new ArrayList<>();
					D instance = __injectClassHelper__().instanciateOne(klass);
					__injectFieldValueSetter__().execute(instance, masterFieldName, master);
					__injectFieldValueSetter__().execute(instance, fieldName, index);
					collection.add(instance);	
				}
			}
		}
		return collection;
	}
	
	@SuppressWarnings("unchecked")
	protected <M,D> void __save__(Class<D> klass,CollectionInstance<M> finalInstances,Collection<M> persistedInstances,String fieldName,Object master,String masterFieldName) {
		Collection<D> collection = __getSavableInstances__(klass, finalInstances, persistedInstances, fieldName, master, masterFieldName);
		if(__injectCollectionHelper__().isNotEmpty(collection))
			__inject__(Business.class).saveMany((Collection<Object>) collection);
	}
	
}
