package org.cyk.utility.server.persistence;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.value.ValueUsageType;

/**
 * 
 * @author Christian
 *
 */
public interface PersistenceEntity<ENTITY> extends PersistenceServiceProvider<ENTITY> {

	/* Create */
	
	/* Read */ 
	ENTITY readOne(Object identifier,Properties properties);
	ENTITY readOne(Object identifier,ValueUsageType valueUsageType);
	ENTITY readOne(Object identifier);
	ENTITY readOneByBusinessIdentifier(Object identifier);
	
	Collection<ENTITY> readMany(Properties properties);
	Collection<ENTITY> readMany();
	
	/* Update */
	
	/* Delete */
	
	/* Count */
	Long count(Properties properties);
	Long count();
	
	/**/
	
	Class<ENTITY> getEntityClass();
}
