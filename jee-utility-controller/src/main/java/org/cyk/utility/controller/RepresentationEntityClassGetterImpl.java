package org.cyk.utility.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RepresentationEntityClassGetterImpl extends RepresentationEntityClassGetter.AbstractImpl implements Serializable {

	public static final Map<Class<?>,Class<?>> MAP = new HashMap<>();
	
	@Override
	public Class<?> get(Class<?> controllerEntityClass) {
		if(MAP.containsKey(controllerEntityClass))
			return MAP.get(controllerEntityClass);
		return super.get(controllerEntityClass);
	}
	
}
