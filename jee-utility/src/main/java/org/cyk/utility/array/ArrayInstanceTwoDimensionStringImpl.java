package org.cyk.utility.array;

import java.io.Serializable;

public class ArrayInstanceTwoDimensionStringImpl extends AbstractArrayInstanceTwoDimensionImpl<String> implements ArrayInstanceTwoDimensionString,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setElementClass(String.class);
	}
	
}
