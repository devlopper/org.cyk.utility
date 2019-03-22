package org.cyk.utility.array;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface ArrayInstance<ARRAY> extends Objectable {

	ARRAY getArray();
	ArrayInstance<ARRAY> setArray(ARRAY array);
	
	Class<?> getElementClass();
	ArrayInstance<ARRAY> setElementClass(Class<?> elementClass);
	
}
