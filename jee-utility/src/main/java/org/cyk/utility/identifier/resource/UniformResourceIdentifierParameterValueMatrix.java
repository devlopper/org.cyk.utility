package org.cyk.utility.identifier.resource;

import java.util.Collection;

import org.cyk.utility.map.MapInstance;
import org.cyk.utility.object.ObjectByStringMap;

public interface UniformResourceIdentifierParameterValueMatrix extends MapInstance<String, ObjectByStringMap> {

	ObjectByStringMap getClassMap();
	
	UniformResourceIdentifierParameterValueMatrix setClasses(Collection<Class<?>> classes);
	UniformResourceIdentifierParameterValueMatrix setClasses(Class<?>...classes);
	UniformResourceIdentifierParameterValueMatrix setClass(Class<?> aClass);
	
}
