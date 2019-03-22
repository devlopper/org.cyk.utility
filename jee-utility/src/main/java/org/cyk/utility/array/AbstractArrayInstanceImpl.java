package org.cyk.utility.array;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public abstract class AbstractArrayInstanceImpl<ARRAY> extends AbstractObject implements ArrayInstance<ARRAY>,Serializable {
	private static final long serialVersionUID = 1L;

	private ARRAY array;
	private Class<?> elementClass;
	
	@Override
	public ARRAY getArray() {
		return array;
	}
	
	@Override
	public ArrayInstance<ARRAY> setArray(ARRAY array) {
		this.array = array;
		return this;
	}
	
	@Override
	public Class<?> getElementClass() {
		return elementClass;
	}
	
	@Override
	public ArrayInstance<ARRAY> setElementClass(Class<?> elementClass) {
		this.elementClass = elementClass;
		return this;
	}
	
}
