package org.cyk.utility.mapping;

import java.io.Serializable;

import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
@Deprecated
public class MapperSourceDestinationClassNameGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<String> implements MapperSourceDestinationClassNameGetter,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Class<?> klass;
	private String className;
	
	@Override
	protected String __execute__() throws Exception {
		String className = getClassName();
		if(Boolean.TRUE.equals(StringHelper.isBlank(className))) {
			Class<?> klass = getKlass();
			if(klass != null)
				className = klass.getName();
		}
		String name = String.format("%sMapper", ValueHelper.returnOrThrowIfBlank("class name", className));
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
	
	@Override
	public String getClassName() {
		return className;
	}
	
	@Override
	public MapperSourceDestinationClassNameGetter setClassName(String className) {
		this.className = className;
		return this;
	}
}
