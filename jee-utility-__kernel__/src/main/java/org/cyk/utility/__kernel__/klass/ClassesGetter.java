package org.cyk.utility.__kernel__.klass;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface ClassesGetter {

	static Collection<Class<?>> getByProperty(Property property) {
		if(CLASSES_BY_PROPERTY.containsKey(property))
			return CLASSES_BY_PROPERTY.get(property);
		Collection<Class<?>> collection = null;
		if(Property.PERSISTABLE.equals(property))
			collection = getPersistable();
		else
			throw new RuntimeException("Get "+property+" classes not yet implemented");
		CLASSES_BY_PROPERTY.put(property, collection);
		return collection;
	}
	
	static Collection<Class<?>> getPersistable() {
		return PersistableClassesGetter.getInstance().get();
	}
	
	Map<Property,Collection<Class<?>>> CLASSES_BY_PROPERTY = new HashMap<>();
}
