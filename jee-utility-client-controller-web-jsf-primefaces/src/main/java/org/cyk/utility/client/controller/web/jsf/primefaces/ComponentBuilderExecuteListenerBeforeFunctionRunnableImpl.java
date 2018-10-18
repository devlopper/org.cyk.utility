package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.ComponentBuilderExecuteListenerBefore;

public class ComponentBuilderExecuteListenerBeforeFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ComponentBuilderExecuteListenerBefore> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public ComponentBuilderExecuteListenerBeforeFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				
			}
		});
	}
	
}