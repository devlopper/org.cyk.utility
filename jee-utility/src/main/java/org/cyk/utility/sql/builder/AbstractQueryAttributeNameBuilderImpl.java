package org.cyk.utility.sql.builder;

import java.io.Serializable;

import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl;

public abstract class AbstractQueryAttributeNameBuilderImpl extends AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl
		implements QueryAttributeNameBuilder, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected String __execute__() throws Exception {
		Attribute attribute = getAttribute();
		String prefix = ConstantEmpty.STRING;
		Boolean isPrefixedWithTuple = getIsPrefixedWithTuple();
		if(isPrefixedWithTuple == null)
			isPrefixedWithTuple = attribute.getIsPrefixedWithTuple();
		if(Boolean.TRUE.equals(isPrefixedWithTuple)){
			if(attribute.getTuple() == null)
				throw new RuntimeException("Sql query attribute name : tuple is required");
			prefix = attribute.getTuple().getAlias()+ConstantCharacter.DOT;
		}
		return prefix+attribute.getName();
	}
	
	@Override
	public QueryAttributeNameBuilder execute(String attributeName, Tuple tuple) {
		setAttribute(attributeName,tuple);
		return (QueryAttributeNameBuilder) execute();
	}
	
	@Override
	public QueryAttributeNameBuilder execute(String attributeName) {
		return execute(attributeName,null);
	}
	
	@Override
	public QueryAttributeNameBuilder setAttribute(Attribute attribute) {
		getProperties().setAttribute(attribute);
		return this;
	}
	
	@Override
	public QueryAttributeNameBuilder setAttribute(String attributeName, Tuple tuple) {
		setAttribute(tuple == null ? new Attribute().setName(attributeName).setIsPrefixedWithTuple(Boolean.FALSE) : tuple.getAttributeByName(attributeName, Boolean.TRUE));
		return this;
	}
	
	@Override
	public QueryAttributeNameBuilder setAttribute(String attributeName) {
		return setAttribute(attributeName, null);
	}

	@Override
	public Attribute getAttribute() {
		return (Attribute) getProperties().getAttribute();
	}
	
	@Override
	public QueryAttributeNameBuilder setIsPrefixedWithTuple(Boolean isPrefixedWithTuple) {
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.PREFIX,Properties.TUPLE}, isPrefixedWithTuple);
		return this;
	}
	
	@Override
	public Boolean getIsPrefixedWithTuple() {
		return (Boolean) getProperties().getFromPath(Properties.IS,Properties.PREFIX,Properties.TUPLE);
	}

}
