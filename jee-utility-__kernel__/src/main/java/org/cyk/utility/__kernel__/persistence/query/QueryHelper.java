package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.persistence.PersistenceHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface QueryHelper {

	static Queries getQueries() {
		return QUERIES;
	}
	
	/* get */
	
	static String getIdentifier(Class<?> klass,String name) {
		if(klass == null || StringHelper.isBlank(name))
			return null;
		Map<String,String> map = IDENTIFIERS.get(klass);
		if(map == null)
			IDENTIFIERS.put(klass, map = new HashMap<>());
		String identifier = map.get(name);
		if(StringHelper.isBlank(identifier))
			map.put(name, identifier = QueryIdentifierBuilder.getInstance().build(klass,name));
		return identifier;
	}
	
	static String getIdentifierReadByFilters(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "readByFilters");
	}
	
	static String getIdentifierCountByFilters(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "countByFilters");
	}
	
	static String getIdentifierReadByFiltersLike(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "readByFiltersLike");
	}
	
	static String getIdentifierCountByFiltersLike(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "countByFiltersLike");
	}
	
	static String getIdentifierReadWhereCodeNotInByFilters(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "readWhereCodeNotInByFilters");
	}
	
	static String getIdentifierCountWhereCodeNotInByFilters(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "countWhereCodeNotInByFilters");
	}
	
	static String getIdentifierReadWhereBusinessIdentifierOrNameContains(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "readWhereBusinessIdentifierOrNameContains");
	}
	
	static String getIdentifierCountWhereBusinessIdentifierOrNameContains(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "countWhereBusinessIdentifierOrNameContains");
	}
	
	/* execute */
	
	static void execute(Query query,Properties properties) {
		EntityManager entityManager = PersistenceHelper.getEntityManager(properties, Boolean.TRUE);
		//Instantiate query
		javax.persistence.Query __query__ = entityManager.createNamedQuery(query.getIdentifier().toString());
		//Set query parameters
		Properties parameters = (Properties) properties.getParameters();
		if(parameters != null && parameters.__getMap__()!=null)
			for(Map.Entry<Object, Object> entry : parameters.__getMap__().entrySet())
				__query__.setParameter(entry.getKey().toString(), entry.getValue());
		//Execute query statement
		Integer count = __query__.executeUpdate();
		properties.setCount(count);
		//TODO This is required when doing batch processing. is it flush not required ?
		entityManager.clear();
	}
	
	/**/
	
	static void clear() {
		IDENTIFIERS.clear();
		QUERIES.removeAll();
	}
	
	Map<Class<?>,Map<String,String>> IDENTIFIERS = new HashMap<>();
	Queries QUERIES = new QueriesImpl();
	
	/**/
	
	public static class QueriesImpl extends Queries implements Serializable {
		
		{
			setCollectionClass(Set.class);
		}
		
		@Override
		protected void __listenAdded__(Collection<Query> queries) {
			super.__listenAdded__(queries);
			EntityManager entityManager = PersistenceHelper.getEntityManager(null);
			for(Query query : queries) {
				Class<?> resultClass = query.getResultClass();
				javax.persistence.Query __query__ = resultClass == null ? entityManager.createQuery(query.getValue()) : entityManager.createQuery(query.getValue(), resultClass);
				entityManager.getEntityManagerFactory().addNamedQuery((String) query.getIdentifier(), __query__);
			}
		}		
	}
}
