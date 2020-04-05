package org.cyk.utility.__kernel__.persistence.query;

import static org.cyk.utility.__kernel__.klass.ClassHelper.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.__kernel__.persistence.PersistenceHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface QueryHelper {

	static Queries getQueries() {
		return QUERIES;
	}
	
	/* scan */
	
	static void scan(Collection<Package> packages) {
		LogHelper.logInfo(String.format("query helper scanning packages %s", packages),QueryHelper.class);
		if(CollectionHelper.isEmpty(packages))
			return;
		Collection<java.lang.Class<?>> classes = filter(packages, List.of(ByDimensionOneSystemIdentifierQuerier.class,ByDimensionOneBusinessIdentifierQuerier.class,ByDimensionTwoQuerier.class),Boolean.TRUE);
		if(CollectionHelper.isEmpty(classes))
			return;
		Collection<Query> queries = null;
		for(Class<?> klass : classes) {
			org.cyk.utility.__kernel__.persistence.query.annotation.Query queryAnnotation = klass.getAnnotation(org.cyk.utility.__kernel__.persistence.query.annotation.Query.class);
			if(queryAnnotation != null) {
				Collection<Query> __queries__ = Query.buildFromAnnotation(queryAnnotation);
				if(CollectionHelper.isNotEmpty(__queries__)) {
					if(queries == null)
						queries = new ArrayList<>();
					queries.addAll(__queries__);
				}
			}
			org.cyk.utility.__kernel__.persistence.query.annotation.Queries queriesAnnotation = klass.getAnnotation(org.cyk.utility.__kernel__.persistence.query.annotation.Queries.class);
			if(queriesAnnotation != null && ArrayHelper.isNotEmpty(queriesAnnotation.value())) {
				Collection<Query> __queries__ = Query.buildFromAnnotation(queriesAnnotation);
				if(CollectionHelper.isNotEmpty(__queries__)) {
					if(queries == null)
						queries = new ArrayList<>();
					queries.addAll(__queries__);		
				}
			}
		}
		addQueries(queries);
		LogHelper.logInfo(String.format("query helper scanned packages %s", packages),QueryHelper.class);
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
	
	static String getIdentifierReadAll(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "read");
	}
	
	static String getIdentifierCountAll(Class<?> klass) {
		if(klass == null)
			return null;
		return getIdentifier(klass, "count");
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
	
	static void addQueries(Collection<Query> queries) {
		if(CollectionHelper.isEmpty(queries))
			return;
		getQueries().add(queries);
		LogHelper.logInfo("Queries added : "+queries.stream().map(Query::getIdentifier).collect(Collectors.toList()), QueryHelper.class);
	}
	
	static void addQueries(Query...queries) {
		if(ArrayHelper.isEmpty(queries))
			return;
		addQueries(CollectionHelper.listOf(queries));
	}
	
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
		QUERIES.removeAll();
		QUERIES.set(null);
		QUERIES.setIsRegisterableToEntityManager(null);
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
			if(Boolean.TRUE.equals(getIsRegisterableToEntityManager())) {
				EntityManager entityManager = DependencyInjection.inject(EntityManagerGetter.class).get();
				if(entityManager == null) {
					LogHelper.logSevere(String.format("we cannot register queries to entitymanager because it is null. %s", queries), getClass());
					return;
				}
				for(Query query : queries) {
					Class<?> resultClass = query.getResultClass();
					javax.persistence.Query __query__ = resultClass == null ? entityManager.createQuery(query.getValue()) : entityManager.createQuery(query.getValue(), resultClass);
					entityManager.getEntityManagerFactory().addNamedQuery((String) query.getIdentifier(), __query__);
				}	
			}else {
				LogHelper.logWarning(String.format("Queries not added to entity manager : %s", queries), QueriesImpl.class);
			}
		}		
	}
}
