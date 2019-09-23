package org.cyk.utility.sql.builder;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.SortOrder;
import org.cyk.utility.__kernel__.properties.Properties;

public abstract class AbstractQueryStringBuilderSelectImpl extends AbstractQueryStringBuilderImpl implements QueryStringBuilderSelect, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __execute__() throws Exception {
		QueryClauseStringBuilderSelect select = getSelectClauseBuilder();
		if(select!=null)
			addChild(select);
		
		QueryClauseStringBuilderFrom from = getFromClauseBuilder();
		if(from!=null)
			addChild(from);
		
		QueryClauseStringBuilderWhere where = getWhereClauseBuilder();
		if(where!=null && where.getPredicateBuilder()!=null)
			addChild(where);
		
		QueryClauseStringBuilderOrderBy orderBy = getOrderByClauseBuilder();
		if(orderBy!=null)
			addChild(orderBy);
		
		return super.__execute__();
	}

	@Override
	public QueryClauseStringBuilderSelect getSelectClauseBuilder() {
		return (QueryClauseStringBuilderSelect) getProperties().getFromPath(Properties.BUILDER,Properties.SELECT);
	}
	
	@Override
	public QueryClauseStringBuilderSelect getSelectClauseBuilder(Boolean injectIfNull) {
		QueryClauseStringBuilderSelect clause = getSelectClauseBuilder();
		if(clause == null && Boolean.TRUE.equals(injectIfNull)){
			setSelectClauseBuilder(clause = ____inject____(QueryClauseStringBuilderSelect.class));
		}
		return clause;
	}

	@Override
	public QueryStringBuilderSelect setSelectClauseBuilder(QueryClauseStringBuilderSelect builder) {
		getProperties().setFromPath(new Object[]{Properties.BUILDER,Properties.SELECT}, builder);
		return this;
	}

	@Override
	public QueryStringBuilderSelect select(Tuple tuple) {
		getSelectClauseBuilder(Boolean.TRUE).addTuples(tuple);
		return this;
	}

	@Override
	public QueryStringBuilderSelect from(Tuple tuple) {
		getSelectClauseBuilder(Boolean.TRUE).addTuples(tuple);
		return (QueryStringBuilderSelect) super.from(tuple);
	}
	
	@Override
	public QueryStringBuilderSelect where(QueryWherePredicateStringBuilder predicateBuilder) {
		return (QueryStringBuilderSelect) super.where(predicateBuilder);
	}
	
	@Override
	public QueryClauseStringBuilderOrderBy getOrderByClauseBuilder() {
		return (QueryClauseStringBuilderOrderBy) getProperties().getFromPath(Properties.BUILDER,Properties.SORT_BY);
	}
	
	@Override
	public QueryClauseStringBuilderOrderBy getOrderByClauseBuilder(Boolean injectIfNull) {
		QueryClauseStringBuilderOrderBy clause = getOrderByClauseBuilder();
		if(clause == null && Boolean.TRUE.equals(injectIfNull)){
			setOrderByClauseBuilder(clause = ____inject____(QueryClauseStringBuilderOrderBy.class));
		}
		return clause;
	}

	@Override
	public QueryStringBuilderSelect setOrderByClauseBuilder(QueryClauseStringBuilderOrderBy builder) {
		getProperties().setFromPath(new Object[]{Properties.BUILDER,Properties.SORT_BY}, builder);
		return this;
	}

	@Override
	public QueryStringBuilderSelect orderBy(Attribute...attributes) {
		getOrderByClauseBuilder(Boolean.TRUE).addAttributes(attributes);
		return this;
	}
	
	@Override
	public QueryStringBuilderSelect orderBy(String...attributeNames) {
		QueryClauseStringBuilderFrom from = getFromClauseBuilder();
		if(from == null || CollectionHelper.getSize(from.getTuples()) != 1){
			getOrderByClauseBuilder(Boolean.TRUE).addAttributesByNames(attributeNames);
		}else{
			Tuple tuple = from.getFirstTuple();
			Collection<Attribute> attributes = tuple.getAttributesByNames(attributeNames, Boolean.TRUE);
			if(attributes!=null)
				getOrderByClauseBuilder(Boolean.TRUE).addAttributes(attributes);
		}
		return this;
	}
	
	@Override
	public QueryStringBuilderSelect orderBy(String attributeName, SortOrder sortOrder) {
		QueryClauseStringBuilderFrom from = getFromClauseBuilder();
		if(from == null || CollectionHelper.getSize(from.getTuples()) != 1){
			orderBy(attributeName);
		}else{
			Tuple tuple = from.getFirstTuple();
			Attribute attribute = tuple.getAttributeByName(attributeName, Boolean.TRUE);
			attribute.setSortOrder(sortOrder);
			orderBy(attribute);
		}
		return this;
	}
	
}
