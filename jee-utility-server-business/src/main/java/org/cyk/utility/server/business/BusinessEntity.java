package org.cyk.utility.server.business;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.value.ValueUsageType;

/**
 * 
 * @author Christian
 *
 */
public interface BusinessEntity<ENTITY> extends BusinessServiceProvider<ENTITY> {

	/* Create */
	
	/* Find */ 
	ENTITY findOne(Object identifier,Properties properties);
	ENTITY findOne(Object identifier,ValueUsageType valueUsageType);
	ENTITY findOne(Object identifier);
	ENTITY findOneByBusinessIdentifier(Object identifier);
	
	Collection<ENTITY> findMany(Properties properties);
	Collection<ENTITY> findMany();
	
	/* Update */
	
	/* Delete */
	
	/* Count */
	Long count(Properties properties);
	Long count();
	
	/**/
	
	Class<ENTITY> getEntityClass();
	
	/**/
	
}
