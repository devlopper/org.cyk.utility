package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.message.MessagesBuilderImpl;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __initialize__(Object object) {
		__inject__(FunctionRunnableMap.class).set(MessagesBuilderImpl.class, MessagesBuilderFunctionRunnableImpl.class);
	}
	
	@Override
	protected void __destroy__(Object object) {}
	
}
