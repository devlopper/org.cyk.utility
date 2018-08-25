package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.FunctionRunnableImpl;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentPostConstructListener;

public class ComponentPostConstructListenerFunctionRunnableImpl extends FunctionRunnableImpl<ComponentPostConstructListener> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public ComponentPostConstructListenerFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				Component component = (Component) getFunction().getObject();
				component.getProperties().setRendered(Boolean.TRUE);
			}
		});
	}
	
}