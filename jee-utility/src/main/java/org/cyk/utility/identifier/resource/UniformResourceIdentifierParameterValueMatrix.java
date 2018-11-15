package org.cyk.utility.identifier.resource;

import org.cyk.utility.map.MapInstance;
import org.cyk.utility.object.ObjectByStringMap;

public interface UniformResourceIdentifierParameterValueMatrix extends MapInstance<String, ObjectByStringMap> {

	ObjectByStringMap getClassMap();
	
	UniformResourceIdentifierParameterValueMatrix setClass(Class<?> aClass);
	
}
