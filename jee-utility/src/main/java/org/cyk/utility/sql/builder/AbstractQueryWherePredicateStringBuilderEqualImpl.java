package org.cyk.utility.sql.builder;

import java.io.Serializable;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;

public abstract class AbstractQueryWherePredicateStringBuilderEqualImpl extends AbstractQueryWherePredicateStringBuilderImpl implements QueryWherePredicateStringBuilderEqual,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setFormat("%s%s%s");
	}
	
	@Override
	public ComparisonOperator getOperator() {
		return (ComparisonOperator) getProperties().getOperator();
	}
	
	@Override
	public QueryWherePredicateStringBuilderEqual setOperator(ComparisonOperator operator) {
		getProperties().setOperator(operator);
		return this;
	}

	@Override
	public QueryWherePredicateStringBuilderEqual addOperandBuilderByAttributeByParameter(String attributeName,ComparisonOperator operator,Tuple tuple, String parameterName) {
		addFormatArgumentObjects(____inject____(QueryOperandStringBuilder.class).setAttributeNameBuilder(attributeName,tuple)
				,operator.getSymbol()
				,____inject____(QueryOperandStringBuilder.class).setParameterNameBuilder(parameterName));
		return this;
	}
	
	@Override
	public QueryWherePredicateStringBuilderEqual addOperandBuilderByAttribute(String attributeName,ComparisonOperator operator,Tuple tuple) {
		addOperandBuilderByAttributeByParameter(attributeName,operator, tuple, attributeName);
		return this;
	}
	
	@Override
	public QueryWherePredicateStringBuilderEqual addOperandBuilderByAttribute(String attributeName,ComparisonOperator operator, String parameterName) {
		return addOperandBuilderByAttributeByParameter(attributeName,operator,getTuple(),parameterName);
	}
	
	@Override
	public QueryWherePredicateStringBuilderEqual addOperandBuilderByAttribute(String attributeName,ComparisonOperator operator) {
		return addOperandBuilderByAttribute(attributeName,operator,getTuple());
	}
}
