package org.cyk.utility.sql.builder;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl;

public abstract class AbstractQueryParameterNameBuilderImpl extends AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl
		implements QueryParameterNameBuilder, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.FORMAT,Properties.REQUIRED}, Boolean.TRUE);
	}
	
	@Override
	protected Collection<Object> __getFormatArguments__(Boolean isFormatRequired,Collection<Object> formatArguments) {
		return __inject__(CollectionHelper.class).isEmpty(formatArguments) ? __inject__(CollectionHelper.class).instanciate(getParameter().getName()) : formatArguments;
	}
	
	@Override
	public QueryParameterNameBuilder setParameter(Parameter parameter) {
		getProperties().setFromPath(new Object[]{Properties.PARAMETER}, parameter);
		return this;
	}
	
	@Override
	public QueryParameterNameBuilder setParameter(String name) {
		setParameter(new Parameter().setName(name));
		return this;
	}
	
	@Override
	public Parameter getParameter() {
		return (Parameter) getProperties().getFromPath(Properties.PARAMETER);
	}
	
	@Override
	public QueryParameterNameBuilder setParameterName(String name) {
		setParameter(new Parameter().setName(name));
		return this;
	}
	
	@Override
	public String getParameterName() {
		return getParameter() == null ? null : getParameter().getName();
	}
	
}
