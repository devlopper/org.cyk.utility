package org.cyk.utility.server.persistence;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
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
	
	PersistenceServiceProvider<OBJECT> deleteAll();
	
	/* Count */
	
	PersistenceServiceProvider<OBJECT> clear();
	PersistenceServiceProvider<OBJECT> flush();
	
	/**/
	
	Collection<PersistenceQuery> getQueries();
	PersistenceServiceProvider<OBJECT> setQueries(Collection<PersistenceQuery> queries);
	PersistenceServiceProvider<OBJECT> addQueries(Collection<PersistenceQuery> queries);
	PersistenceServiceProvider<OBJECT> addQueries(PersistenceQuery...queries);
	
	PersistenceServiceProvider<OBJECT> addQuery(Object identifier,String value,Class<?> resultClass);
	
	PersistenceServiceProvider<OBJECT> addQueryCollectInstances(Object identifier,String value,Class<?> resultClass);
	PersistenceServiceProvider<OBJECT> addQueryCollectInstances(Object identifier,QueryStringBuilder stringBuilder,Class<?> resultClass);
	
	PersistenceServiceProvider<OBJECT> addQueryCountInstancesFromCollection(Object collectionIdentifier);

}
