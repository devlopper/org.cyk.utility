package org.cyk.utility.sql.builder;

import java.io.Serializable;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;

public abstract class AbstractQueryWherePredicateStringBuilderGroupImpl extends AbstractQueryWherePredicateStringBuilderImpl implements QueryWherePredicateStringBuilderGroup,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public QueryWherePredicateStringBuilderGroup addOperandBuilderByAttribute(String attributeName,ComparisonOperator operator, Tuple tuple,String parameterName) {
		addChild(____inject____(QueryWherePredicateStringBuilderEqual.class).addOperandBuilderByAttributeByParameter(attributeName,operator, tuple,parameterName));
		return this;
	}
	
	@Override
	public QueryWherePredicateStringBuilderGroup addOperandBuilderByAttribute(String attributeName,ComparisonOperator operator, Tuple tuple) {
		addChild(____inject____(QueryWherePredicateStringBuilderEqual.class).addOperandBuilderByAttribute(attributeName,operator, tuple));
		return this;
	}
	
	@Override
	public QueryWherePredicateStringBuilderGroup addOperandBuilderByAttribute(String attributeName,ComparisonOperator operator,String parameterName) {
		addOperandBuilderByAttribute(attributeName,operator,getTuple(),parameterName);
		return this;
	}
	
	@Override
	public QueryWherePredicateStringBuilderGroup addOperandBuilderByAttribute(String attributeName,ComparisonOperator operator) {
		addOperandBuilderByAttribute(attributeName,operator,getTuple());
		return this;
	}
	
	@Override
	public QueryWherePredicateStringBuilderGroup addChild(Object... child) {
		return (QueryWherePredicateStringBuilderGroup) super.addChild(child);
	}
	
	@Override
	public QueryWherePredicateStringBuilderGroup lp() {
		return (QueryWherePredicateStringBuilderGroup) super.lp();
	}
	
	@Override
	public QueryWherePredicateStringBuilderGroup rp() {
		return (QueryWherePredicateStringBuilderGroup) super.rp();
	}
	
	@Override
	public QueryWherePredicateStringBuilderGroup and() {
		return (QueryWherePredicateStringBuilderGroup) super.and();
	}
	
	@Override
	public QueryWherePredicateStringBuilderGroup or() {
		return (QueryWherePredicateStringBuilderGroup) super.or();
	}
	
}
