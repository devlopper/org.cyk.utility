package org.cyk.utility.persistence.query;

import static org.cyk.utility.__kernel__.klass.ClassHelper.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.EntityManagerGetter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface QueryManager {
	
	QueryManager scan(Collection<Package> packages);
	
	/*
	QueryManager setEntityManagerFactory(EntityManagerFactory entityManagerFactory);
	EntityManagerFactory getEntityManagerFactory();
	*/
	QueryManager setIsRegisterableToEntityManagerFactory(Boolean isRegisterableToEntityManagerFactory);
	Boolean getIsRegisterableToEntityManagerFactory();
	
	QueryManager setQueries(Collection<Query> queries);
	Collection<Query> getQueries();
	
	QueryManager register(Collection<Query> queries);
	QueryManager register(Query...queries);
	
	QueryManager registerBuildables(Collection<Query> queries);
	QueryManager registerBuildables(Query...queries);
	
	Query getByIdentifier(String identifier);
	
	Boolean isQueryRegistered(String identifier);
	
	void clear();
	
	@Getter @Setter @Accessors(chain=true)
	public static abstract class AbstractImpl extends AbstractObject implements QueryManager,Serializable {		
		protected Boolean isRegisterableToEntityManagerFactory;
		protected Collection<Query> queries;
		
		protected Collection<Query> getQueries(Boolean injectIfNull) {
			if(queries == null && Boolean.TRUE.equals(injectIfNull))
				queries = new ArrayList<>();
			return queries;
		}
		
		@Override
		public QueryManager scan(Collection<Package> packages) {
			if(QueryManager.getInstance().getIsRegisterableToEntityManagerFactory() == null)
				QueryManager.getInstance().setIsRegisterableToEntityManagerFactory(Boolean.TRUE);
			LogHelper.logInfo(String.format("query helper scanning packages %s.Registerable to entity manager=%s"
					, packages,QueryManager.getInstance().getIsRegisterableToEntityManagerFactory()),QueryHelper.class);
			if(CollectionHelper.isEmpty(packages))
				return this;
			Collection<java.lang.Class<?>> classes = filter(packages, List.of(ByDimensionOneSystemIdentifierQuerier.class,ByDimensionOneBusinessIdentifierQuerier.class,ByDimensionTwoQuerier.class,Querier.class),Boolean.TRUE);
			if(CollectionHelper.isEmpty(classes))
				return this;
			Collection<Query> queries = null;
			for(Class<?> klass : classes) {
				org.cyk.utility.persistence.annotation.Query queryAnnotation = klass.getAnnotation(org.cyk.utility.persistence.annotation.Query.class);
				if(queryAnnotation != null) {
					Collection<Query> __queries__ = Query.buildFromAnnotation(queryAnnotation);
					if(CollectionHelper.isNotEmpty(__queries__)) {
						if(queries == null)
							queries = new ArrayList<>();
						queries.addAll(__queries__);
					}
				}
				org.cyk.utility.persistence.annotation.Queries queriesAnnotation = klass.getAnnotation(org.cyk.utility.persistence.annotation.Queries.class);
				if(queriesAnnotation != null && ArrayHelper.isNotEmpty(queriesAnnotation.value())) {
					Collection<Query> __queries__ = Query.buildFromAnnotation(queriesAnnotation);
					if(CollectionHelper.isNotEmpty(__queries__)) {
						if(queries == null)
							queries = new ArrayList<>();
						queries.addAll(__queries__);		
					}
				}
			}
			register(queries);
			LogHelper.logInfo(String.format("query helper scanned packages %s", packages),QueryHelper.class);
			return this;
		}
		
		@Override
		public QueryManager register(Collection<Query> queries) {
			if(CollectionHelper.isEmpty(queries))
				return this;
			__register__(queries);
			return this;
		}
		
		protected void __register__(Collection<Query> queries) {
			getQueries(Boolean.TRUE).addAll(queries);
			queries = queries.stream().filter(query -> Boolean.TRUE.equals(query.getRegisterableToEntityManager())).collect(Collectors.toList());
			if(CollectionHelper.isEmpty(queries))
				return;
			if(!Boolean.TRUE.equals(isRegisterableToEntityManagerFactory)) {
				LogHelper.logWarning(String.format("Queries has not been added to entity manager : %s", queries), getClass());
				return;
			}
			EntityManager entityManager = EntityManagerGetter.getInstance().get();
			if(entityManager == null) {
				LogHelper.logSevere(String.format("we cannot register queries to entitymanager because it is null. %s", queries), getClass());
				return;
			}
			for(Query query : queries) {
				Class<?> resultClass = query.getIntermediateResultClass() == null ? query.getResultClass() : query.getIntermediateResultClass();
				javax.persistence.Query __query__ = null;
				if(resultClass == null) {
					if(Boolean.TRUE.equals(query.getIsNative()))
						__query__ = entityManager.createNativeQuery(query.getValue());
					else
						__query__ = entityManager.createQuery(query.getValue());
				}else
					__query__ = entityManager.createQuery(query.getValue(), resultClass);
				entityManager.getEntityManagerFactory().addNamedQuery(query.getIdentifier(), __query__);
			}
		}
		
		@Override
		public QueryManager register(Query... queries) {
			if(ArrayHelper.isEmpty(queries))
				return this;
			return register(CollectionHelper.listOf(queries));
		}
		
		@Override
		public QueryManager registerBuildables(Collection<Query> queries) {
			if(CollectionHelper.isEmpty(queries))
				return this;
			queries.forEach(query -> {
				query.setRegisterableToEntityManager(Boolean.FALSE);
			});
			return register(queries);
		}
		
		@Override
		public QueryManager registerBuildables(Query... queries) {
			if(ArrayHelper.isEmpty(queries))
				return this;
			return registerBuildables(CollectionHelper.listOf(queries));
		}
		
		@Override
		public Query getByIdentifier(String identifier) {
			if(StringHelper.isBlank(identifier))
				return null;
			if(CollectionHelper.isEmpty(queries))
				return null;
			for(Query query : queries)
				if(identifier.equals(query.getIdentifier()))
					return query;
			return null;
		}
		
		@Override
		public Boolean isQueryRegistered(String identifier) {
			return getByIdentifier(identifier) != null;
		}
		
		@Override
		public void clear() {
			isRegisterableToEntityManagerFactory = null;
			if(queries != null) {
				queries.clear();
				queries = null;
			}
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments implements Serializable {
		private Query query;
		private Boolean usableByEntityManagerFactory;
	}	
	
	/**/
	
	static QueryManager getInstance() {
		return Helper.getInstance(QueryManager.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}