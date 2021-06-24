package org.cyk.utility.persistence.server.hibernate;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.query.Querier;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryIdentifierBuilder;
import org.cyk.utility.persistence.query.QueryName;
import org.cyk.utility.persistence.server.query.executor.DynamicManyExecutor;
import org.cyk.utility.persistence.server.query.executor.DynamicOneExecutor;

public interface DataAuditedQuerier extends Querier.CodableAndNamable<DataAudited> {

	String QUERY_IDENTIFIER_READ_DYNAMIC = QueryIdentifierBuilder.getInstance().build(DataAudited.class, QueryName.READ_DYNAMIC);	
	String QUERY_IDENTIFIER_READ_DYNAMIC_ONE = QueryIdentifierBuilder.getInstance().build(DataAudited.class, QueryName.READ_DYNAMIC_ONE);
	String QUERY_IDENTIFIER_COUNT_DYNAMIC = QueryIdentifierBuilder.getInstance().build(DataAudited.class, QueryName.COUNT_DYNAMIC);
	
	/**/
	
	public static abstract class AbstractImpl extends Querier.CodableAndNamable.AbstractImpl<DataAudited> implements DataAuditedQuerier,Serializable {
		
		@Override
		public DataAudited readOne(QueryExecutorArguments arguments) {
			if(QUERY_IDENTIFIER_READ_DYNAMIC_ONE.equals(arguments.getQuery().getIdentifier()))
				return DynamicOneExecutor.getInstance().read(DataAudited.class,arguments.setQuery(null));
			return super.readOne(arguments);
		}
		
		@Override
		public Collection<DataAudited> readMany(QueryExecutorArguments arguments) {
			if(QUERY_IDENTIFIER_READ_DYNAMIC.equals(arguments.getQuery().getIdentifier()))
				return DynamicManyExecutor.getInstance().read(DataAudited.class,arguments.setQuery(null));
			return super.readMany(arguments);
		}
		
		@Override
		public Long count(QueryExecutorArguments arguments) {
			if(QUERY_IDENTIFIER_COUNT_DYNAMIC.equals(arguments.getQuery().getIdentifier()))
				return DynamicManyExecutor.getInstance().count(DataAudited.class,arguments.setQuery(null));
			return super.count(arguments);
		}
		
		@Override
		protected Class<DataAudited> getKlass() {
			return DataAudited.class;
		}
	}
	
	/**/
	
	static DataAuditedQuerier getInstance() {
		return Helper.getInstance(DataAuditedQuerier.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	static void initialize() {
		
	}
}