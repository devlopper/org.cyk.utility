package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.__kernel__.persistence.query.filter.Filter;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;

public interface Querier {

	String PARAMETER_NAME_IDENTIFIER = "identifier";
	String PARAMETER_NAME_IDENTIFIERS = "identifiers";
	String PARAMETER_NAME_CODE = "code";
	String PARAMETER_NAME_CODES = "codes";
	String PARAMETER_NAME_STRING = "string";
	String PARAMETER_NAME_THIS = "this";
	String PARAMETER_NAME_NAME = "name";

	/**/
	
	<T,I> Collection<T> readBySystemIdentifiers(Class<T> resultClass,Class<I> identifierClass,Collection<I> identifiers);
	<T> Collection<T> readBySystemIdentifiers(Class<T> resultClass,Collection<String> identifiers);
	
	<T,I> Collection<T> readByBusinessIdentifiers(Class<T> resultClass,Class<I> identifierClass,Collection<I> identifiers);
	<T> Collection<T> readByBusinessIdentifiers(Class<T> resultClass,Collection<String> identifiers);
	
	<T> Collection<T> read(Class<T> resultClass,QueryExecutorArguments arguments);
	<T> Long count(Class<T> resultClass,QueryExecutorArguments arguments);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements Querier,Serializable {
		
		private static void validateIdentifiers(Class<?> resultClass, Class<?> identifierClass,Collection<?> identifiers) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("result class", resultClass);
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("identifier class", identifierClass);
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("identifiers", identifiers);
		}
		
		@Override
		public <T, I> Collection<T> readBySystemIdentifiers(Class<T> resultClass, Class<I> identifierClass,Collection<I> identifiers) {
			validateIdentifiers(resultClass, identifierClass, identifiers);
			String queryIdentifier = QueryIdentifierGetter.getInstance().get(resultClass, QueryName.READ_BY_SYSTEM_IDENTIFIERS);
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("read by system identifiers query identifier of "+resultClass, queryIdentifier);
			return QueryExecutor.getInstance().executeReadMany(resultClass, queryIdentifier, PARAMETER_NAME_IDENTIFIERS,identifiers);
		}
		
		@Override
		public <T> Collection<T> readBySystemIdentifiers(Class<T> resultClass, Collection<String> identifiers) {
			return readBySystemIdentifiers(resultClass,String.class, identifiers);
		}
		
		@Override
		public <T, I> Collection<T> readByBusinessIdentifiers(Class<T> resultClass, Class<I> identifierClass,Collection<I> identifiers) {
			validateIdentifiers(resultClass, identifierClass, identifiers);
			String queryIdentifier = QueryIdentifierGetter.getInstance().get(resultClass, QueryName.READ_BY_BUSINESS_IDENTIFIERS);
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("read by business identifiers query identifier of "+resultClass, queryIdentifier);
			return QueryExecutor.getInstance().executeReadMany(resultClass, queryIdentifier, PARAMETER_NAME_IDENTIFIERS,identifiers);
		}
		
		@Override
		public <T> Collection<T> readByBusinessIdentifiers(Class<T> resultClass, Collection<String> identifiers) {
			return readByBusinessIdentifiers(resultClass,String.class, identifiers);
		}
	
		@Override
		public <T> Collection<T> read(Class<T> resultClass, QueryExecutorArguments arguments) {
			if(resultClass == null)
				return null;
			if(arguments == null)
				arguments = QueryExecutorArguments.instantiate(resultClass, QueryName.READ);
			return QueryExecutor.getInstance().executeReadMany(resultClass, arguments);
		}
		
		@Override
		public <T> Long count(Class<T> resultClass,QueryExecutorArguments arguments) {
			if(resultClass == null)
				return null;
			if(arguments == null)
				arguments = QueryExecutorArguments.instantiate(resultClass, QueryName.COUNT);
			return QueryExecutor.getInstance().executeCount(arguments);
		}
	}
	
	public static interface CodableAndNamable<T> extends Querier {
		
		Integer NUMBER_OF_WORDS_OF_PARAMETER_NAME_NAME = 4;
		
		/* read code order by ascending */
		Collection<String> readCodes(QueryExecutorArguments arguments);
		Collection<String> readCodes();
		
		Boolean isOwner(QueryExecutorArguments arguments);
		
		T readOne(QueryExecutorArguments arguments);
		Collection<T> readMany(QueryExecutorArguments arguments);
		Long count(QueryExecutorArguments arguments);
		
		Collection<T> read();
		
		/* read where code or name like order by code ascending */
		Collection<T> readWhereCodeOrNameLike(QueryExecutorArguments arguments);
		
		/* count where filter */
		Long countWhereCodeOrNameLike(QueryExecutorArguments arguments);
		
		static String getQueryValueWhereCodeOrNameLikeFromWhere(String tupleName) {
			return Language.From.of(tupleName+" t")+" "+Language.Where.of(Language.Where.or(
				Language.Where.like("t", AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl.FIELD_CODE, PARAMETER_NAME_CODE)
				,Language.Where.like("t", AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl.FIELD_NAME, PARAMETER_NAME_NAME, NUMBER_OF_WORDS_OF_PARAMETER_NAME_NAME)
				));
		}
		
		static String getQueryValueReadWhereCodeOrNameLike(String tupleName) {
			return Language.of(Language.Select.of("t.identifier,t.code,t.name"),getQueryValueWhereCodeOrNameLikeFromWhere(tupleName),Language.Order.of("t.code ASC"));
		}
		
		
		static String getQueryValueCountWhereCodeOrNameLike(String tupleName) {
			return Language.of(Language.Select.of("COUNT(t.identifier)"),getQueryValueWhereCodeOrNameLikeFromWhere(tupleName));
		}
		
		static String getQueryValueReadCodesFromWhere(String tupleName) {
			return Language.From.of(tupleName+" t");
		}
		
		static String getQueryValueReadCodes(String tupleName) {
			return Language.of(Language.Select.of("t.code"),getQueryValueReadCodesFromWhere(tupleName),Language.Order.of("t.code ASC"));
		}
		
		/**/
		
		public static abstract class AbstractImpl<T> extends Querier.AbstractImpl implements CodableAndNamable<T>,Serializable {
			
			@Override
			public Boolean isOwner(QueryExecutorArguments arguments) {
				if(arguments == null || arguments.getQuery() == null || StringHelper.isBlank(arguments.getQuery().getIdentifier()))
					return null;
				return __isOwner__(arguments);
			}
			
			protected Boolean __isOwner__(QueryExecutorArguments arguments) {
				if(arguments == null || arguments.getQuery() == null)
					return Boolean.FALSE;
				if(getKlass() == null)
					throw new RuntimeException("querier namable class is required");
				return StringUtils.startsWith(arguments.getQuery().getIdentifier(), getKlass().getSimpleName()+".");
			}
			
			@Override
			public T readOne(QueryExecutorArguments arguments) {
				throw new RuntimeException(arguments.getQuery().getIdentifier()+" cannot be processed by "+getClass());
			}
			
			@Override
			public Collection<T> readMany(QueryExecutorArguments arguments) {
				if(QueryIdentifierGetter.getInstance().get(getKlass(), QueryName.READ_WHERE_CODE_OR_NAME_LIKE).equals(arguments.getQuery().getIdentifier()))
					return (Collection<T>) readWhereCodeOrNameLike(arguments);
				throw new RuntimeException(arguments.getQuery().getIdentifier()+" cannot be processed by "+getClass());
			}
			
			@Override
			public Long count(QueryExecutorArguments arguments) {
				if(QueryIdentifierGetter.getInstance().get(getKlass(), QueryName.COUNT_WHERE_CODE_OR_NAME_LIKE).equals(arguments.getQuery().getIdentifier()))
					return countWhereCodeOrNameLike(arguments);
				throw new RuntimeException(arguments.getQuery().getIdentifier()+" cannot be processed by "+getClass());
			}
			
			@Override
			public Collection<T> readWhereCodeOrNameLike(QueryExecutorArguments arguments) {
				prepareWhereFilter(arguments);
				return QueryExecutor.getInstance().executeReadMany(getKlass(), arguments);
			}
			
			@Override
			public Long countWhereCodeOrNameLike(QueryExecutorArguments arguments) {
				prepareWhereFilter(arguments);
				return QueryExecutor.getInstance().executeCount(arguments);
			}
			
			protected void prepareWhereFilter(QueryExecutorArguments arguments) {
				Filter filter = new Filter();
				filter.addFieldContains(PARAMETER_NAME_CODE, arguments);
				filter.addFieldContainsStringOrWords(PARAMETER_NAME_NAME, NUMBER_OF_WORDS_OF_PARAMETER_NAME_NAME, arguments);
				arguments.setFilter(filter);
			}
			
			@Override
			public Collection<String> readCodes(QueryExecutorArguments arguments) {
				if(arguments == null)
					arguments = QueryExecutorArguments.instantiate(getKlass(), QueryName.READ_CODES);
				return QueryExecutor.getInstance().executeReadMany(String.class, arguments);
			}
			
			@Override
			public Collection<String> readCodes() {
				return readCodes(QueryExecutorArguments.instantiate(getKlass(), QueryName.READ_CODES));
			}
			
			@Override
			public Collection<T> read() {
				return super.read(getKlass(), QueryExecutorArguments.instantiate(getKlass(), QueryName.READ));
			}
			
			protected Class<T> getKlass() {
				return (Class<T>) ClassHelper.getParameterAt(getClass(), 0);
			}
		}
		
		/**/
		
		static void initialize(Class<?> klass) {		
			QueryHelper.addQueries(Query.build(Query.FIELD_IDENTIFIER,QueryIdentifierGetter.getInstance().get(klass, QueryName.READ_WHERE_CODE_OR_NAME_LIKE)
					,Query.FIELD_TUPLE_CLASS,klass,Query.FIELD_RESULT_CLASS,klass
					,Query.FIELD_VALUE,Querier.CodableAndNamable.getQueryValueReadWhereCodeOrNameLike(klass.getSimpleName())
					).setTupleFieldsNamesIndexes(MapHelper.instantiateStringIntegerByStrings(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl
							.FIELD_IDENTIFIER,AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl.FIELD_CODE
							,AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl.FIELD_NAME))
				);		
			QueryHelper.addQueries(Query.build(Query.FIELD_IDENTIFIER,QueryIdentifierGetter.getInstance().get(klass, QueryName.COUNT_WHERE_CODE_OR_NAME_LIKE)
					,Query.FIELD_TUPLE_CLASS,klass,Query.FIELD_RESULT_CLASS,Long.class
					,Query.FIELD_VALUE,Querier.CodableAndNamable.getQueryValueCountWhereCodeOrNameLike(klass.getSimpleName())
					)
				);
			
			QueryHelper.addQueries(Query.build(Query.FIELD_IDENTIFIER,QueryIdentifierGetter.getInstance().get(klass, QueryName.READ_CODES)
					,Query.FIELD_TUPLE_CLASS,klass,Query.FIELD_RESULT_CLASS,Object[].class
					,Query.FIELD_VALUE,Querier.CodableAndNamable.getQueryValueReadCodes(klass.getSimpleName())
					)
				);
		}
	}
	
	static String buildIdentifier(Class<?> klass,String name) {
		return QueryIdentifierBuilder.getInstance().build(klass, name);
	}
}