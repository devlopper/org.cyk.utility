package org.cyk.utility.__kernel__.array;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

@Dependent
public class ArrayTwoDimensionString extends ArrayTwoDimension<String> implements Serializable {
	private static final long serialVersionUID = 1L;

	{
		setElementClass(String.class);
	}
	
}
