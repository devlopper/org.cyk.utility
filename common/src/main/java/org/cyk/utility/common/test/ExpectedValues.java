package org.cyk.utility.common.test;

import java.io.Serializable;

import org.cyk.utility.common.ObjectFieldValues;

public class ExpectedValues extends ObjectFieldValues implements Serializable {

	private static final long serialVersionUID = 1284100881789191895L;

	public ExpectedValues(Object...objects){
		set(objects);
	}
}
