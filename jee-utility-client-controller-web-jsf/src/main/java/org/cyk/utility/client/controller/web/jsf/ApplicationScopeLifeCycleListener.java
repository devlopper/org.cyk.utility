package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.component.input.choice.ChoiceBuilderImpl;
import org.cyk.utility.client.controller.context.ContextPropertyValueGetterImpl;
import org.cyk.utility.client.controller.message.MessagesBuilderImpl;
import org.cyk.utility.request.RequestGetterImpl;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		__inject__(FunctionRunnableMap.class).set(MessagesBuilderImpl.class, MessagesBuilderFunctionRunnableImpl.class,LEVEL);
		__inject__(FunctionRunnableMap.class).set(ChoiceBuilderImpl.class, ChoiceBuilderFunctionRunnableImpl.class,LEVEL);
		__inject__(FunctionRunnableMap.class).set(RequestGetterImpl.class, RequestGetterFunctionRunnableImpl.class,LEVEL);
		__inject__(FunctionRunnableMap.class).set(ContextPropertyValueGetterImpl.class,ContextPropertyValueGetterFunctionRunnableImpl.class,LEVEL);
	}
	
	@Override
	public void __destroy__(Object object) {}
	
	/**/
	
	public static final Integer LEVEL = new Integer(org.cyk.utility.client.controller.web.ApplicationScopeLifeCycleListener.LEVEL+1);
}
