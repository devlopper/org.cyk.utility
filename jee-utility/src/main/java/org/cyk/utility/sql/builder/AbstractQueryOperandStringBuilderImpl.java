package org.cyk.utility.sql.builder;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl;

public abstract class AbstractQueryOperandStringBuilderImpl extends
		AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl implements QueryOperandStringBuilder, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected String __execute__() throws Exception {
		QueryAttributeNameBuilder attributeNameBuilder = getAttributeNameBuilder();
		if(attributeNameBuilder!=null)
			return attributeNameBuilder.execute().getOutput();
		Object literal = getLiteral();
		if(literal!=null)
			return literal.toString();
		return getParameterStringBuilder().execute().getOutput();
	}
	
	@Override
	public QueryOperandStringBuilder setAttributeNameBuilder(QueryAttributeNameBuilder builder) {
		getProperties().setFromPath(new Object[]{Properties.ATTRIBUTE,Properties.NAME,Properties.BUILDER}, builder);
		return this;
	}

	@Override
	public QueryAttributeNameBuilder getAttributeNameBuilder() {
		return (QueryAttributeNameBuilder) getProperties().getFromPath(Properties.ATTRIBUTE,Properties.NAME,Properties.BUILDER);
	}

	@Override
	public QueryOperandStringBuilder setLiteral(Object literal) {
		getProperties().setLiteral(literal);
		return this;
	}

	@Override
	public Object getLiteral() {
		return getProperties().getLiteral();
	}

	@Override
	public QueryOperandStringBuilder setParameterStringBuilder(QueryParameterStringBuilder builder) {
		getProperties().setFromPath(new Object[]{Properties.PARAMETER,Properties.BUILDER}, builder);
		return this;
	}

	@Override
	public QueryParameterStringBuilder getParameterStringBuilder() {
		return (QueryParameterStringBuilder) getProperties().getFromPath(Properties.PARAMETER,Properties.BUILDER);
	}

	
	
}
