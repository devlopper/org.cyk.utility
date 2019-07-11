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
	
	@Override PersistenceEntity<ENTITY> create(ENTITY object);
	
	/* Read */ 
	QueryStringBuilderSelect instanciateReadQueryStringBuilder();
	Collection<ENTITY> read(Properties properties);
	Collection<ENTITY> read();
	
	ENTITY readOne(Object identifier,Properties properties);
	ENTITY readOne(Object identifier,ValueUsageType valueUsageType);
	ENTITY readOne(Object identifier);
	ENTITY readOneByBusinessIdentifier(Object identifier);
	ENTITY readOneBySystemIdentifier(Object identifier);
	
	Collection<ENTITY> readMany(Properties properties);
	Collection<ENTITY> readMany();
	
	Collection<Object> readIdentifiers(ValueUsageType valueUsageType,Properties properties);
	Collection<Object> readIdentifiers(ValueUsageType valueUsageType);
	
	Collection<Object> readSystemIdentifiers(Properties properties);
	Collection<Object> readSystemIdentifiers();
	
	Collection<Object> readBusinessIdentifiers(Properties properties);
	Collection<Object> readBusinessIdentifiers();
	
	/* Update */
	
	/* Delete */
	
	PersistenceEntity<ENTITY> deleteByIdentifier(Object identifier,ValueUsageType valueUsageType,Properties properties);
	PersistenceEntity<ENTITY> deleteByIdentifier(Object identifier,ValueUsageType valueUsageType);
	
	PersistenceEntity<ENTITY> deleteBySystemIdentifier(Object identifier,Properties properties);
	PersistenceEntity<ENTITY> deleteBySystemIdentifier(Object identifier);
	
	PersistenceEntity<ENTITY> deleteByBusinessIdentifier(Object identifier,Properties properties);
	PersistenceEntity<ENTITY> deleteByBusinessIdentifier(Object identifier);
	
	PersistenceEntity<ENTITY> deleteByIdentifiers(Collection<Object> identifiers,ValueUsageType valueUsageType,Properties properties);
	PersistenceEntity<ENTITY> deleteManyByIdentifiers(Collection<Object> identifiers,ValueUsageType valueUsageType);
	
	PersistenceEntity<ENTITY> deleteManyBySystemIdentifiers(Collection<Object> identifiers,Properties properties);
	PersistenceEntity<ENTITY> deleteManyBySystemIdentifiers(Collection<Object> identifiers);
	
	PersistenceEntity<ENTITY> deleteManyByBusinessIdentifiers(Collection<Object> identifiers,Properties properties);
	PersistenceEntity<ENTITY> deleteManyByBusinessIdentifiers(Collection<Object> identifiers);
	
	/* Count */
	Long count(Properties properties);
	Long count();
	
	/**/
	
	PersistenceEntity<ENTITY> setEntityClass(Class<ENTITY> aClass);
	Class<ENTITY> getEntityClass();
	
	PersistenceEntity<ENTITY> setIsPhysicallyMapped(Boolean isPhysicallyMapped);
	Boolean getIsPhysicallyMapped();
	
	/**/
	
	PersistenceEntity<ENTITY> addQuery(Object identifier,String value);
	PersistenceEntity<ENTITY> addQueryCollectInstances(Object identifier,String value);
	PersistenceEntity<ENTITY> addQueryCollectInstances(Object identifier,QueryStringBuilder stringBuilder);

}
