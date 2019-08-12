package org.cyk.utility.mapping;

import java.io.Serializable;

import org.cyk.utility.__kernel__.DependencyInjection;

public abstract class AbstractMapperImpl implements Mapper,Serializable {
	private static final long serialVersionUID = 1L;

	protected static <OBJECT> OBJECT __inject__(Class<OBJECT> aClass){
		return DependencyInjection.inject(aClass);
	}
	
}
