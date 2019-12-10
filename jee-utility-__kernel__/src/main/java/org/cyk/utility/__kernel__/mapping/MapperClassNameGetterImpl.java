package org.cyk.utility.__kernel__.mapping;

import java.io.Serializable;

import org.cyk.utility.__kernel__.string.StringHelper;

public class MapperClassNameGetterImpl extends AbstractMapperClassNameGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String get(String className) {
		if(StringHelper.isBlank(className))
			return null;
		return String.format("%sMapper", className);
	}
	
}
