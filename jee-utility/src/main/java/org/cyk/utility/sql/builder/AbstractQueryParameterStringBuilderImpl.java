package org.cyk.utility.sql.builder;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl;

public abstract class AbstractQueryParameterStringBuilderImpl extends AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl
		implements QueryParameterStringBuilder, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.FORMAT,Properties.REQUIRED}, Boolean.TRUE);
	}
	
	@Override
	protected Collection<Object> __getFormatArguments__(Boolean isFormatRequired,Collection<Object> formatArguments) {
		return __inject__(CollectionHelper.class).isEmpty(formatArguments) ? __inject__(CollectionHelper.class).instanciate(getParameterName()) : formatArguments;
	}
	
	@Override
	public QueryParameterStringBuilder setParameterName(String name) {
		getProperties().setFromPath(new Object[]{Properties.PARAMETER,Properties.NAME}, name);
		return this;
	}
	
	@Override
	public String getParameterName() {
		return (String) getProperties().getFromPath(Properties.PARAMETER,Properties.NAME);
	}
	
}
