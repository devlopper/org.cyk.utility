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
	PERSISTENCE_ENTITY findOne(Object identifier,Properties properties);
	PERSISTENCE_ENTITY findOne(Object identifier,ValueUsageType valueUsageType);
	PERSISTENCE_ENTITY findOne(Object identifier);
	PERSISTENCE_ENTITY findOneByBusinessIdentifier(Object identifier);
	PERSISTENCE_ENTITY findOneBySystemIdentifier(Object identifier);
	
	Collection<PERSISTENCE_ENTITY> findMany(Properties properties);
	Collection<PERSISTENCE_ENTITY> findMany();
	
	/* Update */
	
	/* Delete */
	@Transactional
	BusinessEntity<PERSISTENCE_ENTITY> deleteByIdentifier(Object identifier,ValueUsageType valueUsageType);
	
	@Transactional
	BusinessEntity<PERSISTENCE_ENTITY> deleteBySystemIdentifier(Object identifier);
	
	@Transactional
	BusinessEntity<PERSISTENCE_ENTITY> deleteByBusinessIdentifier(Object identifier);
	
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
