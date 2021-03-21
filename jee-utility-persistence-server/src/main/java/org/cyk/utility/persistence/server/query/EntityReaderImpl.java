package org.cyk.utility.persistence.server.query;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.persistence.query.EntityFinder;
import org.cyk.utility.persistence.query.EntityReader;
import org.cyk.utility.persistence.query.QueryExecutor;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryGetter;
import org.cyk.utility.persistence.query.QueryHelper;
import org.cyk.utility.persistence.query.QueryName;
import org.cyk.utility.persistence.query.QueryParameterNameBuilder;
import org.cyk.utility.persistence.query.QueryValueBuilder;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public class EntityReaderImpl extends EntityReader.AbstractImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public <T> Collection<T> readMany(Class<T> tupleClass,QueryExecutorArguments arguments) {
		if(tupleClass == null)
			throw new RuntimeException("Tuple class is required");
		if(arguments == null)
			arguments = new QueryExecutorArguments();
		if(arguments.getQuery() == null) {
			String queryIdentifier = QueryHelper.getIdentifierReadAll(tupleClass);
			arguments.setQuery(QueryGetter.getInstance().get(queryIdentifier));
			if(arguments.getQuery() == null)
				arguments.setQuery(QueryGetter.getInstance().getBySelect(tupleClass, QueryName.READ.getValue(),QueryValueBuilder.getInstance().buildSelect(tupleClass)));
		}
		return QueryExecutor.getInstance().executeReadMany(tupleClass, arguments);
	}
	
	@Override
	public <T> Collection<T> readMany(Class<T> tupleClass,String queryIdentifier,Object...filterFieldsValues) {
		if(tupleClass == null)
			throw new RuntimeException("Tuple class is required");
		QueryExecutorArguments arguments = null;
		if(StringHelper.isNotBlank(queryIdentifier)) {
			arguments = new QueryExecutorArguments();
			arguments.setQuery(QueryGetter.getInstance().get(queryIdentifier));
			if(ArrayHelper.isNotEmpty(filterFieldsValues))
				for(Integer index = 0 ; index < filterFieldsValues.length ; index = index + 2)
					arguments.addFilterField((String)filterFieldsValues[index], filterFieldsValues[index+1]);	
		}		
		return readMany(tupleClass,arguments);
	}
	
	@Override
	public <T> Collection<T> readMany(Class<T> tupleClass) {
		return readMany(tupleClass, null);
	}
	
	@Override
	public <T> T readOne(Class<T> tupleClass, QueryExecutorArguments arguments) {
		if(tupleClass == null)
			throw new RuntimeException("Tuple class is required");
		if(arguments == null)
			throw new RuntimeException("arguments are required");
		return QueryExecutor.getInstance().executeReadOne(tupleClass, arguments);
	}
	
	@Override
	public <T> T readOneBySystemIdentifier(Class<T> tupleClass,Object systemIdentifier) {
		if(tupleClass == null)
			throw new RuntimeException("Tuple class is required");
		if(systemIdentifier == null)
			return null;
		//Use find in order to use cache where possible
		return EntityFinder.getInstance().find(tupleClass, systemIdentifier);
	}
	
	@Override
	public <T> T readOneByBusinessIdentifier(Class<T> tupleClass,Object businessIdentifier) {
		if(tupleClass == null)
			throw new RuntimeException("Tuple class is required");
		if(businessIdentifier == null)
			return null;
		Field field = FieldHelper.getBusinessIdentifier(tupleClass);
		if(field == null)
			throw new RuntimeException("we cannot deduce business identifier field from tuple <<"+tupleClass+">>");
		return readOneWhereFieldIn(tupleClass, QueryName.READ_BY_BUSINESS_IDENTIFIERS.getValue(), field.getName(), businessIdentifier);
	}
	
	@Override
	public <T> T readOneWhereFieldEquals(Class<T> tupleClass,String fieldName,Object fieldValue) {
		if(tupleClass == null)
			throw new RuntimeException("Tuple class is required");
		QueryExecutorArguments arguments = new QueryExecutorArguments();
		arguments.setQuery(QueryGetter.getInstance().getBySelect(tupleClass, String.format("readOneWhere_%s_Equals",fieldName),QueryStringBuilder.getInstance().buildSelectWhereFieldEquals(tupleClass, fieldName)));
		arguments.addFilterField(QueryParameterNameBuilder.getInstance().build(fieldName), fieldValue);
		return readOne(tupleClass,arguments);
	}
	
	@Override
	public <T> T readOneWhereFieldIn(Class<T> tupleClass,String queryName,String fieldName,Object fieldValue) {
		if(tupleClass == null)
			throw new RuntimeException("Tuple class is required");
		QueryExecutorArguments arguments = new QueryExecutorArguments();
		arguments.setQuery(QueryGetter.getInstance().getBySelect(tupleClass, queryName,QueryValueBuilder.getInstance().buildSelectWhereFieldIn(tupleClass, fieldName)));
		arguments.addFilterField(QueryParameterNameBuilder.getInstance().buildPlural(fieldName), fieldValue);
		return readOne(tupleClass,arguments);
	}
	
}
