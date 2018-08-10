package org.cyk.utility.server.business;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
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
	
	Collection<PERSISTENCE_ENTITY> findMany(Properties properties);
	Collection<PERSISTENCE_ENTITY> findMany();
	
	/* Update */
	
	/* Delete */
	BusinessEntity<PERSISTENCE_ENTITY> deleteByIdentifier(Object identifier,ValueUsageType valueUsageType);
	BusinessEntity<PERSISTENCE_ENTITY> deleteBySystemIdentifier(Object identifier);
	BusinessEntity<PERSISTENCE_ENTITY> deleteByBusinessIdentifier(Object identifier);
	
	/* Count */
	Long count(Properties properties);
	Long count();
	
	/**/
	
	Class<PERSISTENCE_ENTITY> getPersistenceEntityClass();
	
	/**/
	
}
