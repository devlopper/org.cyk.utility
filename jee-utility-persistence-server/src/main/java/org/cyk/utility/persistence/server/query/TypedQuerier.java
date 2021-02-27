package org.cyk.utility.persistence.server.query;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.persistence.server.PersistenceHelper;

public interface TypedQuerier<TYPE,IDENTIFIER> extends Querier {

	Class<TYPE> getType();
	
	TYPE readOne(QueryExecutorArguments arguments);
	Collection<TYPE> readMany(QueryExecutorArguments arguments);
	Long count(QueryExecutorArguments arguments);

	Collection<TYPE> readWhereFilter(QueryExecutorArguments arguments);
	Long countWhereFilter(QueryExecutorArguments arguments);
	
	Collection<TYPE> readForUI(QueryExecutorArguments arguments);
	Collection<TYPE> readWhereFilterForUI(QueryExecutorArguments arguments);
	Collection<TYPE> readWhereFilterForEdit(QueryExecutorArguments arguments);
	TYPE readByIdentifierForUI(IDENTIFIER identifier);
	TYPE readByIdentifierForEdit(IDENTIFIER identifier);
	
	public static abstract class AbstractImpl<TYPE,IDENTIFIER> extends Querier.AbstractImpl implements TypedQuerier<TYPE,IDENTIFIER>,Serializable {
		
		@Override
		public TYPE readOne(QueryExecutorArguments arguments) {
			if(QueryType.READ_ONE.equals(arguments.getQuery().getType()))
				return __readOne__(arguments);
			throw getRuntimeExceptionCannotBeProcessed(arguments);
		}
		
		protected TYPE __readOne__(QueryExecutorArguments arguments) {
			return null;
		}
		
		@Override
		public Collection<TYPE> readMany(QueryExecutorArguments arguments) {
			if(QueryType.READ_MANY.equals(arguments.getQuery().getType()))
				return __readMany__(arguments);
			throw getRuntimeExceptionCannotBeProcessed(arguments);
		}
		
		protected Collection<TYPE> __readMany__(QueryExecutorArguments arguments) {
			if(QueryIdentifierGetter.getInstance().get(getType(), QueryName.READ_WHERE_FILTER).equals(arguments.getQuery().getIdentifier()))
				return readWhereFilter(arguments);
			if(QueryIdentifierGetter.getInstance().get(getType(), QueryName.READ_WHERE_FILTER_FOR_UI).equals(arguments.getQuery().getIdentifier()))
				return readWhereFilterForUI(arguments);
			if(QueryIdentifierGetter.getInstance().get(getType(), QueryName.READ_WHERE_FILTER_FOR_EDIT).equals(arguments.getQuery().getIdentifier()))
				return readWhereFilterForEdit(arguments);
			return null;
		}
		
		@Override
		public Long count(QueryExecutorArguments arguments) {
			if(QueryType.COUNT.equals(arguments.getQuery().getType()))
				return __count__(arguments);
			throw getRuntimeExceptionCannotBeProcessed(arguments);
		}
		
		protected Long __count__(QueryExecutorArguments arguments) {
			if(QueryIdentifierGetter.getInstance().get(getType(), QueryName.COUNT_WHERE_FILTER).equals(arguments.getQuery().getIdentifier()))
				return countWhereFilter(arguments);
			return null;
		}
		
		/**/
		
		@Override
		public Collection<TYPE> readWhereFilter(QueryExecutorArguments arguments) {
			arguments = QueryExecutorArguments.setQueryIfNull(arguments, getType(), QueryName.READ_WHERE_FILTER);
			prepareReadWhereFilter(arguments);
			return QueryExecutor.getInstance().executeReadMany(getType(), arguments);
		}
		
		@Override
		public Long countWhereFilter(QueryExecutorArguments arguments) {
			arguments = QueryExecutorArguments.setQueryIfNull(arguments, getType(), QueryName.COUNT_WHERE_FILTER);
			prepareReadWhereFilter(arguments);
			return QueryExecutor.getInstance().executeCount(arguments);
		}
		
		protected void prepareReadWhereFilter(QueryExecutorArguments arguments) {
			Filter filter = new Filter();
			prepareReadWhereFilter(arguments, filter);
			arguments.setFilter(filter);
		}
		
		protected void prepareReadWhereFilter(QueryExecutorArguments arguments,Filter filter) {
			
		}
		
		@Override
		public Collection<TYPE> readForUI(QueryExecutorArguments arguments) {
			return QueryExecutor.getInstance().executeReadMany(getType(), QueryExecutorArguments.setQueryIfNull(arguments, getType(), QueryName.READ_FOR_UI));
		}
		
		@Override
		public Collection<TYPE> readWhereFilterForUI(QueryExecutorArguments arguments) {
			arguments = QueryExecutorArguments.setQueryIfNull(arguments, getType(), QueryName.READ_WHERE_FILTER_FOR_UI);
			prepareReadWhereFilter(arguments);
			return QueryExecutor.getInstance().executeReadMany(getType(), arguments);
		}
		
		@Override
		public Collection<TYPE> readWhereFilterForEdit(QueryExecutorArguments arguments) {
			arguments = QueryExecutorArguments.setQueryIfNull(arguments, getType(), QueryName.READ_WHERE_FILTER_FOR_EDIT);
			prepareReadWhereFilter(arguments);
			return QueryExecutor.getInstance().executeReadMany(getType(), arguments);
		}
		
		@Override
		public TYPE readByIdentifierForUI(IDENTIFIER identifier) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("identifier", identifier);
			return QueryExecutor.getInstance().executeReadOne(getType(), QueryExecutorArguments.instantiate(getType(), QueryName.READ_BY_IDENTIFIER_FOR_UI)
					.addFilterField(PARAMETER_NAME_IDENTIFIER, identifier));
		}
		
		@Override
		public TYPE readByIdentifierForEdit(IDENTIFIER identifier) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("identifier", identifier);
			return QueryExecutor.getInstance().executeReadOne(getType(), QueryExecutorArguments.instantiate(getType(), QueryName.READ_BY_IDENTIFIER_FOR_EDIT)
					.addFilterField(PARAMETER_NAME_IDENTIFIER, identifier));
		}
		
		/**/
		
		protected static RuntimeException getRuntimeExceptionCannotBeProcessed(QueryExecutorArguments arguments) {
			return new RuntimeException(arguments.getQuery().getIdentifier()+" cannot be processed");
		}
	}
	
	static void initialize(Class<?> klass) {
		String tupleName = PersistenceHelper.getEntityName(klass);
		QueryHelper.addQueries(
			Query.buildSelect(klass, QueryIdentifierGetter.getInstance().get(klass, QueryName.READ), String.format("SELECT t FROM %s t",tupleName))
			,Query.buildCount(QueryIdentifierGetter.getInstance().get(klass, QueryName.COUNT), String.format("SELECT COUNT(t) FROM %s t",tupleName))
		);
		
		CountQueryIdentifierGetter.MAP.put(
				QueryIdentifierGetter.getInstance().get(klass, QueryName.READ_FOR_UI), QueryIdentifierGetter.getInstance().get(klass, QueryName.COUNT)
			);
		CountQueryIdentifierGetter.MAP.put(
				QueryIdentifierGetter.getInstance().get(klass, QueryName.READ_WHERE_FILTER_FOR_UI), QueryIdentifierGetter.getInstance().get(klass, QueryName.COUNT_WHERE_FILTER)
			);
		CountQueryIdentifierGetter.MAP.put(
				QueryIdentifierGetter.getInstance().get(klass, QueryName.READ_WHERE_FILTER_FOR_EDIT), QueryIdentifierGetter.getInstance().get(klass, QueryName.COUNT_WHERE_FILTER)
			);
	}
}