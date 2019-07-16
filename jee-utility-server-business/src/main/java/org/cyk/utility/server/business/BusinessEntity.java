package org.cyk.utility.server.business;

import java.io.InputStream;
import java.util.Collection;

import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.array.ArrayInstanceTwoDimensionString;
import org.cyk.utility.file.excel.FileExcelSheetDataArrayReader;
import org.cyk.utility.map.MapInstanceIntegerToString;
import org.cyk.utility.value.ValueUsageType;

/**
 * 
 * @author Christian
 *
 */
public interface BusinessEntity<PERSISTENCE_ENTITY> extends BusinessServiceProvider<PERSISTENCE_ENTITY> {

	/* Create */
	
	/* Read */ 
	Collection<PERSISTENCE_ENTITY> findByIdentifiers(Collection<Object> identifiers,ValueUsageType valueUsageType,Properties properties);
	Collection<PERSISTENCE_ENTITY> findByIdentifiers(Collection<Object> identifiers,ValueUsageType valueUsageType);
	Collection<PERSISTENCE_ENTITY> findByIdentifiers(Collection<Object> identifiers);
	Collection<PERSISTENCE_ENTITY> findByBusinessIdentifiers(Collection<Object> identifiers,Properties properties);
	Collection<PERSISTENCE_ENTITY> findByBusinessIdentifiers(Collection<Object> identifiers);
	Collection<PERSISTENCE_ENTITY> findBySystemIdentifiers(Collection<Object> identifiers,Properties properties);
	Collection<PERSISTENCE_ENTITY> findBySystemIdentifiers(Collection<Object> identifiers);
	
	PERSISTENCE_ENTITY findByIdentifier(Object identifier,ValueUsageType valueUsageType,Properties properties);
	PERSISTENCE_ENTITY findByIdentifier(Object identifier,ValueUsageType valueUsageType);
	PERSISTENCE_ENTITY findByIdentifier(Object identifier);
	PERSISTENCE_ENTITY findByBusinessIdentifier(Object identifier,Properties properties);
	PERSISTENCE_ENTITY findByBusinessIdentifier(Object identifier);
	PERSISTENCE_ENTITY findBySystemIdentifier(Object identifier,Properties properties);
	PERSISTENCE_ENTITY findBySystemIdentifier(Object identifier);
	
	Collection<PERSISTENCE_ENTITY> find(Properties properties);
	Collection<PERSISTENCE_ENTITY> find();
	
	Collection<Object> findIdentifiers(ValueUsageType valueUsageType,Properties properties);
	Collection<Object> findIdentifiers(ValueUsageType valueUsageType);
	
	Collection<Object> findSystemIdentifiers(Properties properties);
	Collection<Object> findSystemIdentifiers();
	
	Collection<Object> findBusinessIdentifiers(Properties properties);
	Collection<Object> findBusinessIdentifiers();
	
	/* Update */
	
	/* Delete */
	@Transactional
	BusinessEntity<PERSISTENCE_ENTITY> deleteByIdentifier(Object identifier,ValueUsageType valueUsageType,Properties properties);
	@Transactional
	BusinessEntity<PERSISTENCE_ENTITY> deleteByIdentifier(Object identifier,ValueUsageType valueUsageType);
	@Transactional
	BusinessEntity<PERSISTENCE_ENTITY> deleteBySystemIdentifier(Object identifier,Properties properties);
	@Transactional
	BusinessEntity<PERSISTENCE_ENTITY> deleteBySystemIdentifier(Object identifier);
	@Transactional
	BusinessEntity<PERSISTENCE_ENTITY> deleteByBusinessIdentifier(Object identifier,Properties properties);
	@Transactional
	BusinessEntity<PERSISTENCE_ENTITY> deleteByBusinessIdentifier(Object identifier);
	@Transactional
	BusinessEntity<PERSISTENCE_ENTITY> deleteByIdentifiers(Collection<Object> identifiers,ValueUsageType valueUsageType,Properties properties);
	@Transactional
	BusinessEntity<PERSISTENCE_ENTITY> deleteByIdentifiers(Collection<Object> identifiers,ValueUsageType valueUsageType);
	@Transactional
	BusinessEntity<PERSISTENCE_ENTITY> deleteBySystemIdentifiers(Collection<Object> identifiers,Properties properties);
	@Transactional
	BusinessEntity<PERSISTENCE_ENTITY> deleteBySystemIdentifiers(Collection<Object> identifiers);
	@Transactional
	BusinessEntity<PERSISTENCE_ENTITY> deleteByBusinessIdentifiers(Collection<Object> identifiers,Properties properties);
	@Transactional
	BusinessEntity<PERSISTENCE_ENTITY> deleteByBusinessIdentifiers(Collection<Object> identifiers);
	
	@Override
	@Transactional
	BusinessEntity<PERSISTENCE_ENTITY> deleteAll();
	
	/**/
	
	BusinessEntity<PERSISTENCE_ENTITY> saveFromArray(ArrayInstanceTwoDimensionString arrayInstanceTwoDimensionString,MapInstanceIntegerToString columnIndexFieldNameMap,Properties properties);
	BusinessEntity<PERSISTENCE_ENTITY> saveFromFileExcelSheet(FileExcelSheetDataArrayReader fileExcelSheetDataArrayReader,MapInstanceIntegerToString columnIndexFieldNameMap,Properties properties);
	BusinessEntity<PERSISTENCE_ENTITY> saveFromFileExcelSheet(InputStream workbookInputStream,String sheetName,MapInstanceIntegerToString columnIndexFieldNameMap,Properties properties);
	
	/* Count */
	Long count(Properties properties);
	Long count();
	
	/**/
	
	Class<PERSISTENCE_ENTITY> getPersistenceEntityClass();
	
	/**/
	
}
