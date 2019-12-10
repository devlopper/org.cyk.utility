package org.cyk.utility.__kernel__.mapping;

import java.io.Serializable;

import javax.annotation.PostConstruct;

public abstract class AbstractMapperImpl implements Mapper,Serializable {
	private static final long serialVersionUID = 1L;
	
	@PostConstruct
	protected void listenPostConstruct() {
		__listenPostConstruct__();
    }
	
    protected void __listenPostConstruct__() {}
	
/*
	protected static <OBJECT> OBJECT __inject__(Class<OBJECT> aClass){
		return DependencyInjection.inject(aClass);
	}
	*/
}
