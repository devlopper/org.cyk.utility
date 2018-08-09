package org.cyk.utility.server.representation;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;

/**
 * 
 * @author Christian
 *
 */
public interface Representation extends RepresentationServiceProvider<Object,Object> {

	/* Create */
	
	/* Find */ 
	/*<ENTITY> ENTITY findOne(Class<ENTITY> aClass,Object identifier,Properties properties);
	<ENTITY> ENTITY findOne(Class<ENTITY> aClass,Object identifier);
	
	<ENTITY> Collection<ENTITY> findMany(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Collection<ENTITY> findMany(Class<ENTITY> aClass);
	*/
	/* Update */
	
	/* Delete */
	
	/* Count */
	/*<ENTITY> Long count(Class<ENTITY> aClass,Properties properties);
	<ENTITY> Long count(Class<ENTITY> aClass);
	*/
}
