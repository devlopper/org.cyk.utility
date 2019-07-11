package org.cyk.utility.mapping;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class MapperSourceDestinationClassNameGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<String> implements MapperSourceDestinationClassNameGetter,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Class<?> klass;
	
	@Override
	protected String __execute__() throws Exception {
		Class<?> klass = __injectValueHelper__().returnOrThrowIfBlank("class", getKlass());
		String name = String.format("%sMapper", klass.getName());
		return name;
	}
	
	@Override
	public Class<?> getKlass() {
		return klass;
	}

	@Override
	public MapperSourceDestinationClassNameGetter setKlass(Class<?> klass) {
		this.klass = klass;
		return this;
	}
	
}
