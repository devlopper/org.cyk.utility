package org.cyk.utility.server.persistence;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.sql.builder.QueryStringBuilder;
import org.cyk.utility.sql.builder.QueryStringBuilderSelect;

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
	
	Collection<ENTITY> readByIdentifiers(Collection<Object> identifiers,ValueUsageType valueUsageType,Properties properties);
	Collection<ENTITY> readByIdentifiers(Collection<Object> identifiers,ValueUsageType valueUsageType);
	Collection<ENTITY> readBySystemIdentifiers(Collection<Object> identifiers,Properties properties);
	Collection<ENTITY> readBySystemIdentifiers(Collection<Object> identifiers);
	Collection<ENTITY> readByBusinessIdentifiers(Collection<Object> identifiers,Properties properties);
	Collection<ENTITY> readByBusinessIdentifiers(Collection<Object> identifiers);
	
	ENTITY readByIdentifier(Object identifier,ValueUsageType valueUsageType,Properties properties);
	ENTITY readByIdentifier(Object identifier,ValueUsageType valueUsageType);
	ENTITY readByIdentifier(Object identifier);
	ENTITY readByBusinessIdentifier(Object identifier,Properties properties);
	ENTITY readByBusinessIdentifier(Object identifier);
	ENTITY readBySystemIdentifier(Object identifier,Properties properties);
	ENTITY readBySystemIdentifier(Object identifier);
	
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
	PersistenceEntity<ENTITY> deleteByIdentifiers(Collection<Object> identifiers,ValueUsageType valueUsageType);
	
	PersistenceEntity<ENTITY> deleteBySystemIdentifiers(Collection<Object> identifiers,Properties properties);
	PersistenceEntity<ENTITY> deleteBySystemIdentifiers(Collection<Object> identifiers);
	
	PersistenceEntity<ENTITY> deleteByBusinessIdentifiers(Collection<Object> identifiers,Properties properties);
	PersistenceEntity<ENTITY> deleteByBusinessIdentifiers(Collection<Object> identifiers);
	
	/* Count */
	Long count(Properties properties);
	Long count();
	
	/**/

	PersistenceEntity<ENTITY> setIsPhysicallyMapped(Boolean isPhysicallyMapped);
	Boolean getIsPhysicallyMapped();
	
	/**/
	
	PersistenceEntity<ENTITY> addQuery(Object identifier,String value);
	PersistenceEntity<ENTITY> addQueryCollectInstances(Object identifier,String value);
	PersistenceEntity<ENTITY> addQueryCollectInstances(Object identifier,QueryStringBuilder stringBuilder);
	
	/**/
	
	String FIELD_NAME_READ_WHERE_CODE_OR_NAME_CONTAINS = "readWhereBusinessIdentifierOrNameContains";
	String FIELD_NAME_COUNT_WHERE_CODE_OR_NAME_CONTAINS = "countWhereBusinessIdentifierOrNameContains";

}
