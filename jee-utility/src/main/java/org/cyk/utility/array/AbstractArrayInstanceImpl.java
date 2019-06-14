package org.cyk.utility.array;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public abstract class AbstractArrayInstanceImpl<T> extends AbstractObject implements ArrayInstance<T>,Serializable {
	private static final long serialVersionUID = 1L;

	private Object array;
	private Class<?> elementClass;
	
	@Override
	public Object getArray() {
		return array;
	}
	
	@Override
	public ArrayInstance<T> setArray(Object array) {
		this.array = array;
		return this;
	}
	
	@Override
	public Class<?> getElementClass() {
		return elementClass;
	}
	
	@Override
	public ArrayInstance<T> setElementClass(Class<?> elementClass) {
		this.elementClass = elementClass;
		return this;
	}
	
}
