package org.cyk.utility.sql.builder;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl;

public abstract class AbstractQueryPredicateStringBuilderImpl extends AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl implements QueryPredicateStringBuilder, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.FORMAT,Properties.REQUIRED}, Boolean.TRUE);
		setFormat("%s%s%s");
	}
	
	@Override
	protected Collection<String> __getFormatArguments__(Boolean isFormatRequired,Collection<String> formatArguments) {
		return __inject__(CollectionHelper.class).isEmpty(formatArguments) ? __inject__(CollectionHelper.class)
				.instanciate(getLeftOperandStringBuilder().execute().getOutput(),getOperator(),getRightOperandStringBuilder().execute().getOutput()) : formatArguments;
	}
	
	@Override
	public QueryPredicateStringBuilder setOperator(String operator) {
		getProperties().setOperator(operator);
		return this;
	}
	
	@Override
	public String getOperator() {
		return (String) getProperties().getOperator();
	}
	
	@Override
	public QueryPredicateStringBuilder setLeftOperandStringBuilder(QueryOperandStringBuilder builer) {
		getProperties().setFromPath(new Object[]{Properties.LEFT,Properties.OPERAND,Properties.BUILDER}, builer);
		return this;
	}
	
	@Override
	public QueryOperandStringBuilder getLeftOperandStringBuilder() {
		return (QueryOperandStringBuilder) getProperties().getFromPath(Properties.LEFT,Properties.OPERAND,Properties.BUILDER);
	}
	
	@Override
	public QueryPredicateStringBuilder setRightOperandStringBuilder(QueryOperandStringBuilder builer) {
		getProperties().setFromPath(new Object[]{Properties.RIGHT,Properties.OPERAND,Properties.BUILDER}, builer);
		return this;
	}
	
	@Override
	public QueryOperandStringBuilder getRightOperandStringBuilder() {
		return (QueryOperandStringBuilder) getProperties().getFromPath(Properties.RIGHT,Properties.OPERAND,Properties.BUILDER);
	}
	
}
