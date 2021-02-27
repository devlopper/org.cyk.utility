package org.cyk.utility.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RepresentationClassGetterImpl extends RepresentationClassGetter.AbstractImpl implements Serializable {

	private static final Map<Class<?>,Map<Function,Class<?>>> MAP = new HashMap<>();
	
	@Override
	protected Class<?> __get__(Class<?> controllerEntityClass, Function function) {
		if(MAP.containsKey(controllerEntityClass))
			return getFromMap(controllerEntityClass, function);
		return super.__get__(controllerEntityClass, function);
	}
	
	private static Class<?> getFromMap(Class<?> controllerEntityClass, Function function) {
		Map<Function,Class<?>> map = MAP.get(controllerEntityClass);
		if(map == null)
			return null;
		return map.get(function);
	}
	
	public static void put(Class<?> controllerEntityClass, Function function,Class<?> klass) {
		Map<Function,Class<?>> map = MAP.get(controllerEntityClass);
		if(map == null)
			MAP.put(controllerEntityClass, map = new HashMap<>());
		map.put(function, klass);
	}
}