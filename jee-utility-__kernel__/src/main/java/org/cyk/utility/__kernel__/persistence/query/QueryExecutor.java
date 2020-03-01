package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface QueryExecutor {

	<T> Collection<T> executeReadMany(Class<T> resultClass,Arguments arguments);
	
	<T> T executeReadOne(Class<T> resultClass,Arguments arguments);
	
	Integer executeUpdateOrDelete(Arguments arguments);
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments implements Serializable {
		private Query query;
		private Map<Object,Object> parameters;
		private Integer firstTupleIndex;
		private Integer numberOfTuples;
		private EntityManager entityManager;
	}
	
	/**/
	
	static QueryExecutor getInstance() {
		return Helper.getInstance(QueryExecutor.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}
