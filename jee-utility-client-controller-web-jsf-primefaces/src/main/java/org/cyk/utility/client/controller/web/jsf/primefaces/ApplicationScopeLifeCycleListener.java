package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.component.ComponentPostConstructListenerImpl;
import org.cyk.utility.client.controller.message.MessageRenderImpl;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __initialize__(Object object) {
		__inject__(FunctionRunnableMap.class).set(ComponentPostConstructListenerImpl.class, ComponentPostConstructListenerFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(MessageRenderImpl.class, MessageRenderFunctionRunnableImpl.class);
	}
	
	@Override
	protected void __destroy__(Object object) {}
	
}
