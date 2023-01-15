package org.cyk.utility.persistence.server.query.string;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.ArithmeticOperator;
import org.cyk.utility.__kernel__.computation.SortOrder;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemImpl;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.__kernel__.object.marker.IdentifiableBusiness;
import org.cyk.utility.__kernel__.object.marker.Namable;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.persistence.SpecificPersistence;
import org.cyk.utility.persistence.query.Field;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.persistence.query.Querier;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryType;
import org.cyk.utility.persistence.server.query.string.WhereStringBuilder.Predicate;

public interface RuntimeQueryStringBuilder {

	String build(QueryExecutorArguments arguments);	
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements RuntimeQueryStringBuilder,Serializable {
		private static final long serialVersionUID = 1L;		
		
		@Override
		public String build(QueryExecutorArguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("arguments", arguments);
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("query", arguments.getQuery());
			return __build__(arguments);
		}
		
		protected String __build__(QueryExecutorArguments arguments) {
			if(StringHelper.isBlank(arguments.getQuery().getValue())) {				
				QueryStringBuilder.Arguments builderArguments = new QueryStringBuilder.Arguments();
				
				setOrder(arguments, builderArguments);
				
				normalizeQueryExecutorArguments(arguments,builderArguments);
				
				setProjection(arguments, builderArguments);
				setTuple(arguments, builderArguments);
				setPredicate(arguments, builderArguments);
				setGroup(arguments, builderArguments);
				
				//Remove all processed keys
				if(arguments.getSortOrders() != null && arguments.getSortOrders().containsKey(SpecificPersistence.PARAMETER_NAME_ORDERED.get())) {
					Map<String,SortOrder> map = new LinkedHashMap<String, SortOrder>(arguments.getSortOrders());
					map.remove(SpecificPersistence.PARAMETER_NAME_ORDERED.get());
					arguments.setSortOrders(map);
				}
				
				return QueryStringBuilder.getInstance().build(builderArguments);
			}else
				return arguments.getQuery().getValue();
		}
		
		protected void normalizeQueryExecutorArguments(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments) {
			arguments.normalize(
					QueryType.COUNT.equals(arguments.getQuery().getType()) ? null : getDefaultProjections(arguments)
					,QueryType.READ_MANY.equals(arguments.getQuery().getType()) 
						? (builderArguments.getOrder() != null ? null : getDefaultSortOrders(arguments)) 
						: null
				);
		}
		
		protected Collection<String> getDefaultProjections(QueryExecutorArguments arguments) {
			Collection<String> projections = new ArrayList<>();			
			projections.add(AbstractIdentifiableSystemImpl.FIELD_IDENTIFIER);
			if(ClassHelper.isInstanceOf(arguments.getQuery().getTupleClass(), IdentifiableBusiness.class))
				projections.add(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl.FIELD_CODE);
			if(ClassHelper.isInstanceOf(arguments.getQuery().getTupleClass(), Namable.class))
				projections.add(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl.FIELD_NAME);			
			return projections;
		}
		
		protected Map<String,SortOrder> getDefaultSortOrders(QueryExecutorArguments arguments) {
			Map<String,SortOrder> sortOrders = new LinkedHashMap<>();
			if(ClassHelper.isInstanceOf(arguments.getQuery().getTupleClass(), IdentifiableBusiness.class))
				sortOrders.put(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl.FIELD_CODE,SortOrder.ASCENDING);
			else if(ClassHelper.isInstanceOf(arguments.getQuery().getTupleClass(), Namable.class))
				sortOrders.put(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl.FIELD_NAME,SortOrder.ASCENDING);
			else
				sortOrders.put(AbstractIdentifiableSystemImpl.FIELD_IDENTIFIER,SortOrder.ASCENDING);
			return sortOrders;
		}
		
		protected void setProjection(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments) {
			if(QueryType.COUNT.equals(arguments.getQuery().getType()))
				builderArguments.getProjection(Boolean.TRUE).add(getProjectionCount(arguments));
			else
				builderArguments.getProjection(Boolean.TRUE).addFromQueryProjections("t", arguments.getProjections());
		}
		
		protected String getProjectionCount(QueryExecutorArguments arguments) {
			return String.format("COUNT(%st.identifier)",Boolean.TRUE.equals(isGroupedByIdentifier(arguments)) ? "DISTINCT " : "");
		}
		
		protected Boolean isGroupedByIdentifier(QueryExecutorArguments arguments) {
			return null;
		}
		
		protected void setTuple(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments) {
			builderArguments.setTupleClass(arguments.getQuery().getTupleClass());
		}
		
		protected void setPredicate(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments) {
			Collection<Field> fields = getPredicateFilterFields(arguments);
			if(CollectionHelper.isNotEmpty(fields)) {
				Predicate predicate = new Predicate().setSeparatorAsAnd();
				Filter filter = new Filter();
				populatePredicate(arguments,builderArguments, predicate,filter);
				if(Predicate.isEmpty(predicate))
					throw new RuntimeException(String.format("Some fields do not have generate predicate. <<%s>>",fields.stream().map(f -> f.getName()).collect(Collectors.joining(","))));
				arguments.setFilter(filter);
				builderArguments.setPredicate(predicate);
			}			
		}
		
		protected Collection<Field> getPredicateFilterFields(QueryExecutorArguments arguments) {
			if(arguments.getFilter() == null)
				return null;
			return getPredicateFilterFields(arguments,arguments.getFilter().getFields());
		}
		
		protected Collection<Field> getPredicateFilterFields(QueryExecutorArguments arguments,Collection<Field> fields) {
			return fields;
		}
		
		protected void populatePredicate(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments,Predicate predicate,Filter filter) {
			addEqualsIfFilterHasFieldWithPath(arguments, builderArguments, predicate, filter, Querier.PARAMETER_NAME_IDENTIFIER);
			
			if(arguments.getFilter().hasFieldWithPath(Querier.PARAMETER_NAME_IDENTIFIERS)) {
				filter.addField(Querier.PARAMETER_NAME_IDENTIFIERS, arguments.getFilterFieldValue(Querier.PARAMETER_NAME_IDENTIFIERS));
				predicate.add(String.format("%s.%s IN :%s", "t",AbstractIdentifiableSystemImpl.FIELD_IDENTIFIER,Querier.PARAMETER_NAME_IDENTIFIERS));
			}
		}
		
		protected void setGroup(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments) {
			
		}
		
		protected void setOrder(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments) {
			
		}
		
		/**/
		
		protected static Boolean isQueryExecutorArgumentsFilterHasFieldWithPath(QueryExecutorArguments arguments,String path) {
			return arguments == null || arguments.getFilter() == null ? null : arguments.getFilter().hasFieldWithPath(path);
		}
		
		protected static Boolean isQueryExecutorArgumentsFilterHasFieldsWithPaths(QueryExecutorArguments arguments,Collection<String> paths) {
			if(arguments == null || arguments.getFilter() == null || CollectionHelper.isEmpty(paths))
				return null;
			for(String path : paths)
				if(!Boolean.TRUE.equals(isQueryExecutorArgumentsFilterHasFieldWithPath(arguments, path)))
					return null;
			return Boolean.TRUE;
		}
		
		protected static Boolean isQueryExecutorArgumentsFilterHasFieldsWithPaths(QueryExecutorArguments arguments,String...paths) {
			if(arguments == null || arguments.getFilter() == null || ArrayHelper.isEmpty(paths))
				return null;
			return isQueryExecutorArgumentsFilterHasFieldsWithPaths(arguments, CollectionHelper.listOf(paths));
		}
		
		/**/
		
		public static Boolean addIfFilterHasFieldWithPath(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments,Predicate predicate,Filter filter
				,String path,String variable,String fieldName) {
			if(arguments == null || arguments.getFilter() == null)
				return null;
			Field field = arguments.getFilter().getField(path);
			if(field == null)
				return null;
			ArithmeticOperator arithmeticOperator = ValueHelper.defaultToIfNull(field.getArithmeticOperator(), ArithmeticOperator.EQ);
			if(ArithmeticOperator.EQ.equals(arithmeticOperator))
				return addEqualsIfFilterHasFieldWithPath(arguments, builderArguments, predicate, filter, path);
			if(ArithmeticOperator.LIKE.equals(arithmeticOperator))
				return addLikeIfFilterHasFieldWithPath(arguments, builderArguments, predicate, filter, path, variable, fieldName);
			return null;
		}
		
		public static Boolean addIfFilterHasFieldWithPath(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments,Predicate predicate,Filter filter
				,String path,String variable) {
			return addIfFilterHasFieldWithPath(arguments, builderArguments, predicate, filter, path, variable, path);
		}
		
		public static Boolean addIfFilterHasFieldWithPath(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments,Predicate predicate,Filter filter
				,String path) {
			return addIfFilterHasFieldWithPath(arguments, builderArguments, predicate, filter, path, "t", path);
		}
		
		public static Boolean addLikeIfFilterHasFieldWithPath(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments,Predicate predicate,Filter filter
				,String path,String variable,String fieldName) {
			Field field = arguments.getFilterField(path);
			if(field == null)
				return null;
			filter.addField(path, LikeStringValueBuilder.getInstance().build((String)field.getValue(), field.getLikeStartsWithAny(), field.getLikeEndsWithAny()));
			predicate.add(LikeStringBuilder.getInstance().build(new LikeStringBuilder.Arguments().setVariable(variable).setFieldName(fieldName).setParameterName(path)
					.setNegate(field.getNegate())));
			/*
			LikeStringBuilder.Arguments likeArguments = new LikeStringBuilder.Arguments();
			LikeStringBuilder.getInstance().build(likeArguments);
			predicate.add(String.format("%s.%s LIKE :%s", variable,fieldName,path));
			*/
			return Boolean.TRUE;
		}
		
		public static Boolean addLikeIfFilterHasFieldWithPath(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments,Predicate predicate,Filter filter
				,String path,String variable) {
			return addLikeIfFilterHasFieldWithPath(arguments, builderArguments, predicate, filter, path, variable, path);
		}
		
		public static Boolean addLikeIfFilterHasFieldWithPath(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments,Predicate predicate,Filter filter
				,String path) {
			return addLikeIfFilterHasFieldWithPath(arguments, builderArguments, predicate, filter, path, "t");
		}
		
		public static Boolean addEqualsIfFilterHasFieldWithPath(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments,Predicate predicate,Filter filter
				,String path,String variable,String fieldName) {
			Field field = arguments.getFilterField(path);
			if(field == null)
				return null;
			filter.addFieldEquals(path, arguments);
			predicate.add(EqualStringBuilder.getInstance().build(new EqualStringBuilder.Arguments().setTupleName(variable).setFieldName(fieldName).setParameterName(path)
					.setNegate(field.getNegate()))); 
			//String.format("%s.%s = :%s", variable,fieldName,path));
			return Boolean.TRUE;
		}
		
		public static void addEqualsIfFilterHasFieldWithPath(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments,Predicate predicate,Filter filter
				,String path,String variable) {
			addEqualsIfFilterHasFieldWithPath(arguments, builderArguments, predicate, filter, path, variable, path);
		}
		
		public static Boolean addEqualsIfFilterHasFieldWithPath(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments,Predicate predicate,Filter filter
				,String path) {
			return addEqualsIfFilterHasFieldWithPath(arguments, builderArguments, predicate, filter, path, "t", path);
		}
		
		public static void addIsFalseOrNullIfFilterHasFieldWithPath(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments,Predicate predicate,Filter filter
				,String path,String variable,String fieldName) {
			if(arguments.getFilter().hasFieldWithPath(path)) {
				//filter.addFieldEquals(path, arguments);
				predicate.add(String.format("(%1$s.%2$s = false OR %1$s.%2$s IS NULL)", variable,fieldName,path));
			}
		}
		
		public static void addIsTrueIfFilterHasFieldWithPath(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments,Predicate predicate,Filter filter
				,String path,String variable,String fieldName) {
			if(arguments.getFilter().hasFieldWithPath(path)) {
				//filter.addFieldEquals(path, arguments);
				predicate.add(String.format("(%1$s.%2$s = true)", variable,fieldName,path));
			}
		}
	}
	
	/**/
	
	/**/
	
	static RuntimeQueryStringBuilder getInstance() {
		return Helper.getInstance(RuntimeQueryStringBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}