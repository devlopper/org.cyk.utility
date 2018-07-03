package org.cyk.utility.server.persistence.jpa;

import java.util.Collection;

import org.cyk.utility.server.persistence.Persistence;

public interface EntityPersistence<ENTITY extends AbstractEntity> extends Persistence {

	/* Create */
	EntityPersistence<ENTITY> create(ENTITY entity);
	
	/* Read */
	ENTITY read(Long identifier);
	Collection<ENTITY> readAll();
	
	/* Update */
	EntityPersistence<ENTITY> update(ENTITY entity);
	
	/* Delete */
	EntityPersistence<ENTITY> delete(ENTITY entity);
	
	/* Count */
	Long countAll();
}
