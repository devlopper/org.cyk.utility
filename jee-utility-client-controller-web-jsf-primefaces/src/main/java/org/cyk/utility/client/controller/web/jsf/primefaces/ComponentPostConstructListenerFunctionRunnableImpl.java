package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentPostConstructListener;
import org.cyk.utility.random.RandomHelper;

public class ComponentPostConstructListenerFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ComponentPostConstructListener> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String IDENTIFIER_FORMAT = "%s_%s";
	
	public ComponentPostConstructListenerFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				//TODO to be deleted. use only builders
				Component component = (Component) getFunction().getObject();
				component.getProperties().setIdentifier(String.format(IDENTIFIER_FORMAT,component.getClass().getSimpleName(),__inject__(RandomHelper.class).getAlphabetic(3)));
				//component.getProperties().setRendered(Boolean.TRUE);
			}
		});
	}
	
}