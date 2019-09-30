package org.cyk.utility.sql.builder;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.LogicalOperator;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.criteria.Criteria;
import org.cyk.utility.filter.Filter;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;

public abstract class AbstractQueryClauseStringBuilderWhereImpl extends AbstractQueryClauseStringBuilderImpl implements QueryClauseStringBuilderWhere, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setKeyword("WHERE");
		//getProperties().setFromPath(new Object[]{Properties.IS,Properties.TUPLE,Properties.REQUIRED}, Boolean.TRUE);
	}
	
	@Override
	protected String __execute__() throws Exception {
		if(getPredicateBuilder()==null){
			QueryWherePredicateStringBuilder predicateBuilder = null;
			Filter filter = getFilter();
			if(filter != null){
				predicateBuilder = __inject__(QueryWherePredicateStringBuilderGroup.class);
				Collection<Object> children = filter.getChildren();
				if(children != null){
					//QueryWherePredicateStringBuilder predicateBuilderSub = (QueryWherePredicateStringBuilder) __inject__(QueryWherePredicateStringBuilderEqual.class);
					for(Object index : children){
						if(index instanceof Criteria){
							//TODO why Equal ??? we must decide on operator ?
							predicateBuilder.addChild(__inject__(QueryWherePredicateStringBuilderEqual.class).setCriteria((Criteria) index));
						}else if(index instanceof LogicalOperator){
							predicateBuilder.addChild(index);
						}
					}
				}
			}
			setPredicateBuilder(predicateBuilder);
		}
		return super.__execute__();
	}
	
	@Override
	protected Collection<String> __executeGetArguments__(Collection<Tuple> tuples, Collection<String> arguments) {
		QueryWherePredicateStringBuilder predicateBuilder = getPredicateBuilder();
		if(predicateBuilder == null)
			throw new RuntimeException("Sql query clause where : predicate is required");
		return CollectionHelper.isEmpty(arguments) ? List.of(predicateBuilder.execute().getOutput()) 
				: super.__executeGetArguments__(tuples, arguments);
	}
	
	@Override
	public QueryWherePredicateStringBuilder getPredicateBuilder() {
		return (QueryWherePredicateStringBuilder) getProperties().getFromPath(Properties.PREDICATE,Properties.BUILDER);
	}

	@Override
	public QueryClauseStringBuilderWhere setPredicateBuilder(QueryWherePredicateStringBuilder builder) {
		getProperties().setFromPath(new Object[]{Properties.PREDICATE,Properties.BUILDER}, builder);
		return this;
	}
	
	@Override
	public QueryClauseStringBuilderWhere setFilter(Filter filter) {
		getProperties().setFromPath(new Object[]{Properties.FILTER},filter);
		return this;
	} 
	
	@Override
	public Filter getFilter() {
		return  (Filter) getProperties().getFromPath(Properties.FILTER);
	}
}
