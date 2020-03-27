package org.cyk.utility.__kernel__.mapping;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MapperClassGetterImpl extends MapperClassGetter.AbstractImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final Map<Class<?>,Class<?>> MAP = new HashMap<>();
	
	@Override
	protected Class<?> __get__(Class<?> klass) {
		if(MAP.containsKey(klass))
			return MAP.get(klass);
		return super.__get__(klass);
	}	
	
}
