package org.cyk.utility.__kernel__.klass;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;

public enum Property {

	PERSISTABLE
	
	;
	
	public static void setProperty(Class<?> klass,Property property,Object value) {
		if(klass == null || property == null)
			return;
		Map<Property,Object> map = MAP.get(klass);
		if(map == null)
			MAP.put(klass, map = new HashMap<>());
		map.put(property, value);
	}
	
	public static Object getProperty(Class<?> klass,Property property) {
		if(klass == null || property == null)
			return null;
		Map<Property,Object> map = MAP.get(klass);
		if(map == null)
			MAP.put(klass, map = new HashMap<Property, Object>());
		Object value = map.get(property);
		if(value == null) {
			if(PERSISTABLE.equals(property)) {
				value = klass.getAnnotation(Entity.class) != null;
			}
			map.put(property, value);
		}
		return value;
	}
	
	public static void clear() {
		MAP.clear();
	}
	
	private static final Map<Class<?>,Map<Property,Object>> MAP = new HashMap<>();
}
