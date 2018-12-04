package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.component.input.choice.ChoiceBuilderImpl;
import org.cyk.utility.client.controller.message.MessagesBuilderImpl;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		__inject__(FunctionRunnableMap.class).set(MessagesBuilderImpl.class, MessagesBuilderFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(ChoiceBuilderImpl.class, ChoiceBuilderFunctionRunnableImpl.class);
	}
	
	@Override
	public void __destroy__(Object object) {}
	
}
