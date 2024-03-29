package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;

public interface QueryExecutor {

	<T> Collection<T> executeReadMany(Class<T> resultClass,QueryExecutorArguments arguments);
	
	default <T> Collection<T> executeReadMany(Class<T> resultClass) {
		return executeReadMany(resultClass, new QueryExecutorArguments().setQuery(QueryGetter.getInstance().getBySelect(resultClass)));
	}
	
	default <T> Collection<T> executeReadMany(Class<T> resultClass,String queryIdentifier,Object...filterFieldsValues) {
		if(resultClass == null)
			throw new RuntimeException("Result class is required");
		return executeReadMany(resultClass, new QueryExecutorArguments().setQueryFromIdentifier(queryIdentifier).addFilterFieldsValues(filterFieldsValues));
	}
	
	default <T> T executeReadOne(Class<T> resultClass,QueryExecutorArguments arguments) {
		Collection<T> collection = executeReadMany(resultClass, arguments);
		if(CollectionHelper.getSize(collection) > 1)
			throw new RuntimeException(String.format("read.one : too much result found after executing query %s with filter %s",arguments.getQuery(),arguments.get__parameters__()));
		return CollectionHelper.getFirst(collection);
	}
	
	Long executeCount(QueryExecutorArguments arguments);
	
	default Long executeCount(Class<?> tupleClass) {
		return executeCount(new QueryExecutorArguments().setQuery(QueryGetter.getInstance().getByCount(tupleClass)));
	}
	
	default Long executeCount(String queryIdentifier,Object...filterFieldsValues) {
		return executeCount(new QueryExecutorArguments().setQueryFromIdentifier(queryIdentifier).addFilterFieldsValues(filterFieldsValues));
	}
	
	Integer executeUpdateOrDelete(QueryExecutorArguments arguments);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements QueryExecutor,Serializable {
		public static Boolean LOGGABLE = Boolean.TRUE;
		public static Level LOG_LEVEL = Level.FINEST;
				
		@Override
		public <T> Collection<T> executeReadMany(Class<T> resultClass, QueryExecutorArguments arguments) {
			validatePreConditions(resultClass, arguments);
			arguments.prepare(resultClass);
			TypedQuery<?> typedQuery = __getTypedQuery__(arguments.get__resultClass__(), arguments.getQuery(),arguments.get__parameters__(),arguments.getFirstTupleIndex()
					,arguments.getNumberOfTuples(),arguments.get__hints__(),arguments.get__entityManager__(),LOGGABLE,ValueHelper.defaultToIfNull(arguments.getLoggingLevel(),LOG_LEVEL));
			Long t = System.currentTimeMillis();
			Collection<?> result = typedQuery.getResultList();
			Long duration = System.currentTimeMillis() - t;
			Collection<T> collection;
			if(CollectionHelper.isEmpty(result))
				collection = null;
			else {
				if(resultClass.equals(arguments.get__resultClass__()))
					collection = (Collection<T>) result;
				else {
					QueryResultMapper.Arguments resultMapperArguments = new QueryResultMapper.Arguments().setQuery(arguments.getQuery());
					if(Tuple.class.equals(arguments.getQuery().getResultClass()))
						resultMapperArguments.setTuples((Collection<Tuple>) result);
					else
						resultMapperArguments.setObjects((Collection<Object[]>) result);
					collection = QueryResultMapper.getInstance().map(resultClass, resultMapperArguments);
				}
			}
			arguments.set__objects__(collection);
			arguments.finalise();
			if(CollectionHelper.isNotEmpty(collection))
				collection = processResult(resultClass, arguments, collection);
			if(Boolean.TRUE.equals(LOGGABLE)) {
				LogHelper.log(String.format("collection size = %s , duration = %s", CollectionHelper.getSize(collection),TimeHelper.formatDuration(duration)), LOG_LEVEL,getClass());
			}
			return collection;
		}
		
		protected <T> Collection<T> processResult(Class<T> resultClass, QueryExecutorArguments arguments,Collection<T> collection) {
			return collection;
		}
		
		@Override
		public Long executeCount(QueryExecutorArguments arguments) {
			validatePreConditions(Long.class, arguments);
			//we do not need pagination for count query
			if(arguments != null) {
				arguments.setFirstTupleIndex(null);
				arguments.setNumberOfTuples(null);
			}
			Long count = executeReadOne(Long.class, arguments);
			return count;
		}
		
		@Override
		public Integer executeUpdateOrDelete(QueryExecutorArguments arguments) {
			validatePreConditions(Void.class, arguments);
			javax.persistence.Query query = __getQuery__(arguments.getQuery().getIdentifier(),arguments.getQuery().getValue(), arguments.getEntityManager());
			return query.executeUpdate();
		}

		/**/
		
		protected void validatePreConditions(Class<?> resultClass, QueryExecutorArguments arguments) {
			if(resultClass == null)
				throw new RuntimeException("result class is required");
			if(arguments == null)
				throw new RuntimeException("arguments are required");
			if(arguments.getQuery() == null)
				throw new RuntimeException("query is required");
		}
		
		protected <T> TypedQuery<T> __getTypedQuery__(Class<T> resultClass, Query query,Map<Object,Object> parameters,Integer firstTupleIndex,Integer numberOfTuples,Map<String,Object> hints,EntityManager entityManager,Boolean loggable,java.util.logging.Level loggingLevel) {
			if(Boolean.TRUE.equals(loggable)) {
				Collection<String> strings = new ArrayList<>();
				if(StringHelper.isNotBlank(query.getIdentifier()))
					strings.add(query.getIdentifier());
				if(resultClass != null)
					strings.add(resultClass.getSimpleName());
				if(query != null)
					strings.add(query.getValue());
				if(MapHelper.isNotEmpty(parameters))
					strings.add(parameters.toString());
				if(firstTupleIndex != null)
					strings.add("Page("+firstTupleIndex+","+numberOfTuples+")");
				if(MapHelper.isNotEmpty(hints))
					strings.add(hints.toString());
				LogHelper.log(StringHelper.concatenate(strings,">"),loggingLevel,getClass());
			}
			if(resultClass == null)
				throw new IllegalArgumentException("result class is required");
			if(query == null)
				throw new IllegalArgumentException("query is required");
			if(StringHelper.isBlank(query.getIdentifier()) && StringHelper.isBlank(query.getValue()))
				throw new IllegalArgumentException("query identifier or query value is required");
			if(entityManager == null)
				entityManager = EntityManagerGetter.getInstance().get();
			//Query query = QueryGetter.getInstance().get(resultClass, queryIdentifier, queryValue);
			TypedQuery<T> typedQuery = (TypedQuery<T>) (StringHelper.isBlank(query.getIdentifier()) ? entityManager.createQuery(query.getValue(), resultClass) 
					: entityManager.createNamedQuery(query.getIdentifier(), resultClass));
			if(firstTupleIndex != null && firstTupleIndex >= 0)
				typedQuery.setFirstResult(firstTupleIndex);
			if(numberOfTuples != null && numberOfTuples >= 1)
				typedQuery.setMaxResults(numberOfTuples);
			
			if(MapHelper.isNotEmpty(parameters))
				for(Map.Entry<Object,Object> entry : parameters.entrySet()) {
					if(entry.getKey() instanceof String) {
						typedQuery.setParameter((String) entry.getKey(), entry.getValue());
					}
				}
			if(MapHelper.isNotEmpty(hints)) {
				hints.forEach( (key,value) -> {
					typedQuery.setHint(key, value);
				} );
			}
			return typedQuery;
		}
		
		protected javax.persistence.Query __getQuery__(String queryIdentifier,String queryValue,EntityManager entityManager) {
			if(StringHelper.isBlank(queryIdentifier) && StringHelper.isBlank(queryValue))
				throw new IllegalArgumentException("query identifier or query value is required");
			if(entityManager == null)
				entityManager = DependencyInjection.inject(EntityManagerGetter.class).get();
			Query query = QueryGetter.getInstance().get(Void.class, queryIdentifier, queryValue);
			return (javax.persistence.Query) (StringHelper.isBlank(queryIdentifier) ? entityManager.createQuery(query.getValue()) 
					: entityManager.createNamedQuery(queryIdentifier));
		}		
	}
	
	/**/
	
	static QueryExecutor getInstance() {
		return Helper.getInstance(QueryExecutor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}