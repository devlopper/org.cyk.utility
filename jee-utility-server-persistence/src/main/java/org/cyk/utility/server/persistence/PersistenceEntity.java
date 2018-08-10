package org.cyk.utility.server.persistence;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.sql.builder.QueryStringBuilder;
import org.cyk.utility.sql.builder.QueryStringBuilderSelect;
import org.cyk.utility.value.ValueUsageType;

/**
 * 
 * @author Christian
 *
 */
public interface PersistenceEntity<ENTITY> extends PersistenceServiceProvider<ENTITY> {

	/* Create */
	
	/* Read */ 
	QueryStringBuilderSelect instanciateReadQueryStringBuilder();
	Collection<ENTITY> read(Properties properties);
	Collection<ENTITY> read();
	
	ENTITY readOne(Object identifier,Properties properties);
	ENTITY readOne(Object identifier,ValueUsageType valueUsageType);
	ENTITY readOne(Object identifier);
	ENTITY readOneByBusinessIdentifier(Object identifier);
	
	Collection<ENTITY> readMany(Properties properties);
	Collection<ENTITY> readMany();
	
	/* Update */
	
	/* Delete */
	
	PersistenceEntity<ENTITY> deleteByIdentifier(Object identifier,ValueUsageType valueUsageType,Properties properties);
	PersistenceEntity<ENTITY> deleteByIdentifier(Object identifier,ValueUsageType valueUsageType);
	
	PersistenceEntity<ENTITY> deleteBySystemIdentifier(Object identifier,Properties properties);
	PersistenceEntity<ENTITY> deleteBySystemIdentifier(Object identifier);
	
	PersistenceEntity<ENTITY> deleteByBusinessIdentifier(Object identifier,Properties properties);
	PersistenceEntity<ENTITY> deleteByBusinessIdentifier(Object identifier);
	
	/* Count */
	Long count(Properties properties);
	Long count();
	
	/**/
	
	PersistenceEntity<ENTITY> setEntityClass(Class<ENTITY> aClass);
	Class<ENTITY> getEntityClass();
	
	/**/
	
	PersistenceEntity<ENTITY> addQuery(Object identifier,String value);
	PersistenceEntity<ENTITY> addQueryCollectInstances(Object identifier,String value);
	PersistenceEntity<ENTITY> addQueryCollectInstances(Object identifier,QueryStringBuilder stringBuilder);

}
