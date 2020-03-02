package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.persistence.query.filter.Filter;
import org.cyk.utility.__kernel__.throwable.Message;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface QueryExecutor {

	<T> Collection<T> executeReadMany(Class<T> resultClass,Arguments arguments);
	
	default <T> Collection<T> executeReadMany(Class<T> resultClass) {
		return executeReadMany(resultClass, new Arguments().setQuery(QueryGetter.getInstance().getBySelect(resultClass)));
	}
	
	default <T> T executeReadOne(Class<T> resultClass,Arguments arguments) {
		Collection<T> collection = executeReadMany(resultClass, arguments);
		if(CollectionHelper.getSize(collection) > 1)
			throw new RuntimeException().addMessages(new Message().setSummary(String.format("Too much result found after executing query %s",arguments.getQuery())));
		return CollectionHelper.getFirst(collection);
	}
	
	Long executeCount(Arguments arguments);
	
	Integer executeUpdateOrDelete(Arguments arguments);
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments implements Serializable {
		private Query query;
		private Map<Object,Object> parameters;
		private Filter filter;
		private Integer firstTupleIndex;
		private Integer numberOfTuples;
		private EntityManager entityManager;
		
		public Map<Object,Object> getParameters(Boolean injectIfNull) {
			if(parameters == null && Boolean.TRUE.equals(injectIfNull))
				parameters = new HashMap<>();
			return parameters;
		}
		
		public Arguments setParameters(Object...objects) {
			MapHelper.set(getParameters(Boolean.TRUE), objects);
			return this;
		}
		
		public Filter getFilter(Boolean injectIfNull) {
			if(filter == null && Boolean.TRUE.equals(injectIfNull))
				filter = new Filter();
			return filter;
		}
		
		public Arguments addFilterField(String fieldName, Object fieldValue) {
			getFilter(Boolean.TRUE).addField(fieldName, fieldValue);
			return this;
		}
	}
	
	/**/
	
	static QueryExecutor getInstance() {
		return Helper.getInstance(QueryExecutor.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}
