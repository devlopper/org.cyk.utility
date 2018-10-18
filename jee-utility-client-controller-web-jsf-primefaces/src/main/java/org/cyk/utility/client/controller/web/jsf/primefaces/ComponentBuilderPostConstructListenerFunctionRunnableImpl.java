package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.ComponentBuilderPostConstructListener;
import org.cyk.utility.random.RandomHelper;

public class ComponentBuilderPostConstructListenerFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ComponentBuilderPostConstructListener> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String IDENTIFIER_FORMAT = "%s_%s";
	
	public ComponentBuilderPostConstructListenerFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				ComponentBuilder<?> componentBuilder = (ComponentBuilder<?>) getFunction().getObject();
				componentBuilder.getOutputProperties(Boolean.TRUE).setIdentifier(String.format(IDENTIFIER_FORMAT,componentBuilder.getComponentClass().getSimpleName(),__inject__(RandomHelper.class).getAlphabetic(3)));
				componentBuilder.getOutputProperties(Boolean.TRUE).setRendered(Boolean.TRUE);				
			}
		});
	}
	
}