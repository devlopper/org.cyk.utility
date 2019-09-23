package org.cyk.utility.mapping;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@SuppressWarnings("rawtypes")
public class MapperSourceDestinationClassGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Class> implements MapperSourceDestinationClassGetter,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Class<?> klass;
	
	@Override
	protected Class<?> __execute__() throws Exception {
		Class<?> klass = __injectValueHelper__().returnOrThrowIfBlank("class", getKlass());
		Class<?> result = org.cyk.utility.__kernel__.klass.ClassHelper.getByName(__inject__(MapperSourceDestinationClassNameGetter.class).setKlass(klass).execute().getOutput());
		if(result == null) {
			if(klass.getName().endsWith("Impl"))
				result = org.cyk.utility.__kernel__.klass.ClassHelper.getByName(__inject__(MapperSourceDestinationClassNameGetter.class).setClassName(
						StringUtils.substringBeforeLast(klass.getName(), "Impl")).execute().getOutput());
		}
		return result;
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
