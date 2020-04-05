package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityReader {

	<T> Collection<T> readMany(Class<T> tupleClass,QueryExecutorArguments arguments);
	
	default <T> Collection<T> readMany(Class<T> tupleClass) {
		if(tupleClass == null)
			return null;
		return readMany(tupleClass,null);
	}
	/*
	default <T> T readOne(Class<T> tupleClass,QueryExecutorArguments arguments) {
		if(tupleClass == null)
			throw new RuntimeException("Tuple class is required");
		if(arguments == null)
			arguments = new QueryExecutorArguments();
		if(arguments.getQuery() == null) {
			String queryIdentifier = QueryHelper.getIdentifierReadBySystemIdentifiers(tupleClass);
			arguments.setQuery(QueryHelper.getQueries().getByIdentifier(queryIdentifier));
			if(arguments.getQuery() == null)
				arguments.setQuery(QueryGetter.getInstance().get(tupleClass, queryIdentifier,String.format("SELECT tuple FROM %s tuple WHERE tuple.identifier IN :identifiers",tupleClass.getSimpleName())));
		}
		return QueryExecutor.getInstance().executeReadOne(tupleClass, arguments);
	}
	*/
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements EntityReader,Serializable {
		private static final long serialVersionUID = 1L;		
		
		public <T> Collection<T> readMany(Class<T> tupleClass,QueryExecutorArguments arguments) {
			if(tupleClass == null)
				throw new RuntimeException("Tuple class is required");
			if(arguments == null)
				arguments = new QueryExecutorArguments();
			if(arguments.getQuery() == null) {
				String queryIdentifier = QueryHelper.getIdentifierReadAll(tupleClass);
				arguments.setQuery(QueryHelper.getQueries().getByIdentifier(queryIdentifier));
				if(arguments.getQuery() == null)
					arguments.setQuery(QueryGetter.getInstance().get(tupleClass, queryIdentifier,String.format("SELECT tuple FROM %s tuple",tupleClass.getSimpleName())));
			}
			return QueryExecutor.getInstance().executeReadMany(tupleClass, arguments);
		}
	}
	
	/**/
	
	static EntityReader getInstance() {
		return Helper.getInstance(EntityReader.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);	
}