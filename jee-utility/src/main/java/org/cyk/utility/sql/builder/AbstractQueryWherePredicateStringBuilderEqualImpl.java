package org.cyk.utility.sql.builder;

import java.io.Serializable;

public abstract class AbstractQueryWherePredicateStringBuilderEqualImpl extends AbstractQueryWherePredicateStringBuilderImpl implements QueryWherePredicateStringBuilderEqual,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setFormat("%s=%s");
	}

	@Override
	public QueryWherePredicateStringBuilderEqual addOperandBuilderByAttributeByParameter(String attributeName,Tuple tuple, String parameterName) {
		addFormatArgumentObjects(____inject____(QueryOperandStringBuilder.class).setAttributeNameBuilder(attributeName,tuple)
				,____inject____(QueryOperandStringBuilder.class).setParameterNameBuilder(parameterName));
		return this;
	}
	
	@Override
	public QueryWherePredicateStringBuilderEqual addOperandBuilderByAttribute(String attributeName,Tuple tuple) {
		addOperandBuilderByAttributeByParameter(attributeName, tuple, attributeName);
		return this;
	}
}
