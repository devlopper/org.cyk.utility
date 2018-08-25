package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.client.controller.AbstractObject;

public abstract class AbstractComponentImpl extends AbstractObject implements Component,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		__inject__(ComponentPostConstructListener.class).setObject(this).execute();
	}
	
}
