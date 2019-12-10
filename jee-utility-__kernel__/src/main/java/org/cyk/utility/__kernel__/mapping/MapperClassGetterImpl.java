package org.cyk.utility.__kernel__.mapping;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class MapperClassGetterImpl extends AbstractMapperClassGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Class<?> get(Class<?> klass) {
		if(klass == null)
			return null;
		Class<?> result = org.cyk.utility.__kernel__.klass.ClassHelper.getByName(MapperClassNameGetter.getInstance().get(klass));
		if(result == null) {
			if(klass.getName().endsWith("Impl"))
				result = org.cyk.utility.__kernel__.klass.ClassHelper.getByName(MapperClassNameGetter.getInstance().get(StringUtils.substringBeforeLast(klass.getName(), "Impl")));
		}
		return result;
	}
	
}
