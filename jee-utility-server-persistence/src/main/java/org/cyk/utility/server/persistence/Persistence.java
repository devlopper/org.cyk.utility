package org.cyk.utility.server.persistence;

import java.util.Collection;

public interface Persistence {

	/* Create */
	Persistence create(Object entity);
	
	/* Read */ 
	<ENTITY> ENTITY read(Class<ENTITY> aClass,Object identifier);
	<ENTITY> Collection<ENTITY> readAll(Class<ENTITY> aClass);
	
	/* Update */
	Persistence update(Object entity);
	
	/* Delete */
	Persistence delete(Object entity);
	
	/* Count */
	<ENTITY> Long countAll(Class<ENTITY> aClass);
	
}
