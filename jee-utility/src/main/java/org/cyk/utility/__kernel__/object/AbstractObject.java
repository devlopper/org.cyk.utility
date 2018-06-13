package org.cyk.utility.__kernel__.object;

import java.io.Serializable;

import org.cyk.utility.__kernel__.DependencyInjection;

public abstract class AbstractObject implements Objectable,Serializable {
	private static final long serialVersionUID = 1L;

	protected static <OBJECT> OBJECT __inject__(Class<OBJECT> aClass){
		return DependencyInjection.inject(aClass);
	}
	
}
