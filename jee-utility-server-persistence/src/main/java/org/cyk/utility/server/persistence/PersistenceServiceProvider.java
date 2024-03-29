package org.cyk.utility.server.persistence;

import java.util.Collection;

import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.sql.builder.QueryStringBuilder;
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
	
	PersistenceServiceProvider<OBJECT> deleteAll(Properties properties);
	PersistenceServiceProvider<OBJECT> deleteAll();
	
	/* Save */
	PersistenceServiceProvider<OBJECT> save(OBJECT object,Properties properties);
	PersistenceServiceProvider<OBJECT> save(OBJECT object);
	
	PersistenceServiceProvider<OBJECT> saveMany(Collection<OBJECT> objects,Properties properties);
	PersistenceServiceProvider<OBJECT> saveMany(Collection<OBJECT> objects);
	
	/* Count */
	
	/**/
	
	Collection<Query> getQueries();
	PersistenceServiceProvider<OBJECT> setQueries(Collection<Query> queries);
	PersistenceServiceProvider<OBJECT> addQueries(Collection<Query> queries);
	PersistenceServiceProvider<OBJECT> addQueries(Query...queries);
	
	PersistenceServiceProvider<OBJECT> addQuery(Object identifier,String value,Class<?> resultClass);
	
	PersistenceServiceProvider<OBJECT> addQueryCollectInstances(Object identifier,String value,Class<?> resultClass);
	PersistenceServiceProvider<OBJECT> addQueryCollectInstances(Object identifier,QueryStringBuilder stringBuilder,Class<?> resultClass);
	
	PersistenceServiceProvider<OBJECT> addQueryCountInstancesFromCollection(Object collectionIdentifier);

}
