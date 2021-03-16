package org.cyk.utility.persistence.query;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.persistence.PersistenceHelper;

public interface QueryHelper {

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
	
	static String getIdentifierReadAll(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, QueryName.READ.getValue());
	}
	
	static String getIdentifierCountAll(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, QueryName.COUNT.getValue());
	}
	
	static String getIdentifierReadBySystemIdentifiers(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "readBySystemIdentifiers");
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
	
	/* add */
	/*
	static void addQueries(Collection<Query> queries) {
		if(CollectionHelper.isEmpty(queries))
			return;
		getQueries().add(queries);
		LogHelper.logFine("Queries added : "+queries.stream().map(Query::getIdentifier).collect(Collectors.toList()), QueryHelper.class);
	}
	
	static void addQueries(Query...queries) {
		if(ArrayHelper.isEmpty(queries))
			return;
		addQueries(CollectionHelper.listOf(queries));
	}
	*/
	/*
	static void addQueries(Class<?> klass,Map<QueryName,String> values) {
		if(MapHelper.isNotEmpty(values)) {
			for(Map.Entry<QueryName,String> entry : values.entrySet()) {
				Query query;
				if(entry.getKey().name().startsWith("READ_"))
					query = Query.buildSelect(klass, QueryIdentifierGetter.getInstance().get(klass, entry.getKey()), entry.getValue());
				else
					query = Query.buildCount(QueryIdentifierGetter.getInstance().get(klass, entry.getKey()), entry.getValue());
				QueryHelper.addQueries(query);
			}
		}
	}
	*/
	/**/

	
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
		QueryManager.getInstance().clear();
	}
	
	Map<Class<?>,Map<String,String>> IDENTIFIERS = new HashMap<>();
	
	//Queries QUERIES = new QueriesImpl();
	
	/**/
	
	/*@Deprecated
	public static class QueriesImpl extends Queries implements Serializable {
		
		{
			setCollectionClass(Set.class);
		}
		
		@Override
		protected void __listenAdded__(Collection<Query> queries) {
			super.__listenAdded__(queries);
			if(CollectionHelper.isEmpty(queries))
				return;
			queries = queries.stream().filter(query -> Boolean.TRUE.equals(query.getRegisterableToEntityManager())).collect(Collectors.toList());
			if(CollectionHelper.isEmpty(queries))
				return;
			
			if(Boolean.TRUE.equals(getIsRegisterableToEntityManager())) {
				EntityManager entityManager = DependencyInjection.inject(EntityManagerGetter.class).get();
				if(entityManager == null) {
					LogHelper.logSevere(String.format("we cannot register queries to entitymanager because it is null. %s", queries), getClass());
					return;
				}
				for(Query query : queries) {
					Class<?> resultClass = query.getIntermediateResultClass() == null ? query.getResultClass() : query.getIntermediateResultClass();
					javax.persistence.Query __query__ = resultClass == null ? entityManager.createQuery(query.getValue()) : entityManager.createQuery(query.getValue(), resultClass);
					entityManager.getEntityManagerFactory().addNamedQuery(query.getIdentifier(), __query__);
				}	
			}else {
				LogHelper.logWarning(String.format("Queries has not been added to entity manager : %s", queries), QueriesImpl.class);
			}
		}		
	}*/
}
