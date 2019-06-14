package org.cyk.utility.array;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface ArrayInstance<T> extends Objectable {

	Object getArray();
	ArrayInstance<T> setArray(Object array);
	
	Class<?> getElementClass();
	ArrayInstance<T> setElementClass(Class<?> elementClass);
	
}
