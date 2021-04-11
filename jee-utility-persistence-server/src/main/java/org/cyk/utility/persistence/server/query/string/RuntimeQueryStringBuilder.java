package org.cyk.utility.persistence.server.query.string;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
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
import org.cyk.utility.persistence.query.Filter;
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
				normalizeQueryExecutorArguments(arguments);
				QueryStringBuilder.Arguments builderArguments = new QueryStringBuilder.Arguments();
				setProjection(arguments, builderArguments);
				setTuple(arguments, builderArguments);
				setPredicate(arguments, builderArguments);
				return QueryStringBuilder.getInstance().build(builderArguments);
			}else
				return arguments.getQuery().getValue();
		}
		
		protected void normalizeQueryExecutorArguments(QueryExecutorArguments arguments) {
			arguments.normalize(
					QueryType.COUNT.equals(arguments.getQuery().getType()) ? null : getDefaultProjections(arguments)
					,QueryType.COUNT.equals(arguments.getQuery().getType()) ? null : getDefaultSortOrders(arguments)
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
				builderArguments.getProjection(Boolean.TRUE).add("COUNT(t.identifier)");
			else
				builderArguments.getProjection(Boolean.TRUE).addFromQueryProjections("t", arguments.getProjections());
		}
		
		protected void setTuple(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments) {
			builderArguments.setTupleClass(arguments.getQuery().getTupleClass());
		}
		
		protected void setPredicate(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments) {
			if(arguments.getFilter() != null && CollectionHelper.isNotEmpty(arguments.getFilter().getFields())) {
				Predicate predicate = new Predicate().setSeparatorAsAnd();
				Filter filter = new Filter();
				populatePredicate(arguments,builderArguments, predicate,filter);
				arguments.setFilter(filter);
				builderArguments.setPredicate(predicate);
			}			
		}
		
		protected void populatePredicate(QueryExecutorArguments arguments,QueryStringBuilder.Arguments builderArguments,Predicate predicate,Filter filter) {
			
		}
	}
	
	/**/
	
	/**/
	
	static RuntimeQueryStringBuilder getInstance() {
		return Helper.getInstance(RuntimeQueryStringBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}