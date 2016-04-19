package org.cyk.utility.common;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractBuilder<OBJECT> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 2176086848500759488L;

	protected Class<OBJECT> aClass;
	protected OBJECT instance;
	
	public AbstractBuilder(Class<OBJECT> aClass) {
		super();
		this.aClass = aClass;
	}

	public AbstractBuilder<OBJECT> instanciate(){
		instance = newInstance(aClass);
		return this;
	}
	
	public abstract OBJECT build();
	
}
