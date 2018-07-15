package org.cyk.utility.sql.builder;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl;

public abstract class AbstractQueryPredicateStringBuilderImpl extends AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl implements QueryPredicateStringBuilder, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.FORMAT,Properties.REQUIRED}, Boolean.TRUE);
		//setFormat("%s%s%s");
	}
	
	@Override
	public QueryPredicateStringBuilder addOperandStringBuilderAttributeName(String attributeName, Tuple tuple) {
		addFormatArgumentObjects(____inject____(QueryOperandStringBuilder.class).setAttributeNameBuilder(attributeName, tuple));
		return this;
	}
	
	@Override
	public QueryPredicateStringBuilder addOperandStringBuilderParameterName(String parameterName) {
		addFormatArgumentObjects(____inject____(QueryOperandStringBuilder.class).setParameterNameBuilder(parameterName));
		return this;
	}
	
}
