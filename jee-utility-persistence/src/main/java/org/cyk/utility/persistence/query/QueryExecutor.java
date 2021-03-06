package org.cyk.utility.persistence.query;

import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface QueryExecutor {

	<T> Collection<T> executeReadMany(Class<T> resultClass,QueryExecutorArguments arguments);
	
	<T> Collection<T> executeReadMany(Class<T> resultClass);
	
	<T> Collection<T> executeReadMany(Class<T> resultClass,String queryIdentifier,Object...filterFieldsValues);
	
	<T> T executeReadOne(Class<T> resultClass,QueryExecutorArguments arguments);
	
	Long executeCount(QueryExecutorArguments arguments);
	
	default Long executeCount(Class<?> tupleClass) {
		return executeCount(new QueryExecutorArguments().setQuery(QueryGetter.getInstance().getByCount(tupleClass)));
	}
	
	default Long executeCount(String queryIdentifier,Object...filterFieldsValues) {
		return executeCount(new QueryExecutorArguments().setQueryFromIdentifier(queryIdentifier).addFilterFieldsValues(filterFieldsValues));
	}
	
	Integer executeUpdateOrDelete(QueryExecutorArguments arguments);

	/**/
	
	static QueryExecutor getInstance() {
		return Helper.getInstance(QueryExecutor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}