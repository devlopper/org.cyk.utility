package org.cyk.utility.mapping;

import java.io.Serializable;

import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@SuppressWarnings("rawtypes")
public class MapperSourceDestinationClassGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Class> implements MapperSourceDestinationClassGetter,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Class<?> klass;
	
	@Override
	protected Class<?> __execute__() throws Exception {
		Class<?> klass = __injectValueHelper__().returnOrThrowIfBlank("class", getKlass());
		return __inject__(ClassHelper.class).getByName(__inject__(MapperSourceDestinationClassNameGetter.class).setKlass(klass).execute().getOutput());
	}
	
	@Override
	public Class<?> getKlass() {
		return klass;
	}

	@Override
	public MapperSourceDestinationClassGetter setKlass(Class<?> klass) {
		this.klass = klass;
		return this;
	}
	
}
