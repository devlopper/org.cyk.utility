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
		return getParameterNameBuilder().execute().getOutput();
	}
	
	@Override
	public QueryOperandStringBuilder executeAttribute(String attributeName, Tuple tuple) {
		setAttributeNameBuilder(____inject____(QueryAttributeNameBuilder.class).setAttribute(attributeName, tuple));
		return (QueryOperandStringBuilder) execute();
	}
	
	@Override
	public QueryOperandStringBuilder executeLiteral(Object literal) {
		setLiteral(literal);
		return (QueryOperandStringBuilder) execute();
	}
	
	@Override
	public QueryOperandStringBuilder executeParameter(String name) {
		setParameterNameBuilder(____inject____(QueryParameterNameBuilder.class).setParameter(name));
		return (QueryOperandStringBuilder) execute();
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
	public QueryOperandStringBuilder setAttributeNameBuilder(String attributeName, Tuple tuple) {
		setAttributeNameBuilder(____inject____(QueryAttributeNameBuilder.class).setAttribute(attributeName, tuple));
		return this;
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
	public QueryOperandStringBuilder setParameterNameBuilder(QueryParameterNameBuilder builder) {
		getProperties().setFromPath(new Object[]{Properties.PARAMETER,Properties.BUILDER}, builder);
		return this;
	}

	@Override
	public QueryParameterNameBuilder getParameterNameBuilder() {
		return (QueryParameterNameBuilder) getProperties().getFromPath(Properties.PARAMETER,Properties.BUILDER);
	}

	@Override
	public QueryOperandStringBuilder setParameterNameBuilder(String parameterName) {
		setParameterNameBuilder(____inject____(QueryParameterNameBuilder.class).setParameter(parameterName));
		return this;
	}
	
}
