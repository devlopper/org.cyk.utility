package org.cyk.utility.server.persistence;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.SystemServiceProvider;

public interface PersistenceServiceProvider<OBJECT> extends SystemServiceProvider {

	/* Create */
	PersistenceServiceProvider<OBJECT> create(OBJECT object,Properties properties);
	PersistenceServiceProvider<OBJECT> create(OBJECT object);
	
	PersistenceServiceProvider<OBJECT> createMany(Collection<OBJECT> objects,Properties properties);
	PersistenceServiceProvider<OBJECT> createMany(Collection<OBJECT> objects);
	
	/* Read */ 
	
	/* Update */
	PersistenceServiceProvider<OBJECT> update(OBJECT object,Properties properties);
	PersistenceServiceProvider<OBJECT> update(OBJECT object);
	
	PersistenceServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects,Properties properties);
	PersistenceServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects);
	
	/* Delete */
	PersistenceServiceProvider<OBJECT> delete(OBJECT object,Properties properties);
	PersistenceServiceProvider<OBJECT> delete(OBJECT object);
	
	PersistenceServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects,Properties properties);
	PersistenceServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects);
	
	/* Count */
	
}
