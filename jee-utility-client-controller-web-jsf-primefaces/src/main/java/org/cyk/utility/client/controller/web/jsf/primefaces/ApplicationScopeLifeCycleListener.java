package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.command.CommandFunction;
import org.cyk.utility.client.controller.component.ComponentBuilderExecuteListenerAfterImpl;
import org.cyk.utility.client.controller.component.ComponentBuilderExecuteListenerBeforeImpl;
import org.cyk.utility.client.controller.component.ComponentBuilderPostConstructListenerImpl;
import org.cyk.utility.client.controller.component.ComponentPostConstructListenerImpl;
import org.cyk.utility.client.controller.component.ComponentRoleStyleClassGetterImpl;
import org.cyk.utility.client.controller.component.ComponentTargetModelBuilderImpl;
import org.cyk.utility.client.controller.component.grid.GridBuilderCommandableBuilderProcessorImpl;
import org.cyk.utility.client.controller.component.layout.LayoutWidthGetterImpl;
import org.cyk.utility.client.controller.component.layout.StyleClassBuilderWidthCssPrimefacesGridFunctionRunnableImpl;
import org.cyk.utility.client.controller.message.MessageRenderImpl;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierToUrlStringMapperImpl;
import org.cyk.utility.client.controller.navigation.NavigationRedirectorImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.annotation.Primefaces;
import org.cyk.utility.css.StyleClassBuilderWidthImpl;
import org.cyk.utility.request.RequestParameterValueGetterImpl;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		__inject__(FunctionRunnableMap.class).set(ComponentPostConstructListenerImpl.class, ComponentPostConstructListenerFunctionRunnableImpl.class,LEVEL);
		__inject__(FunctionRunnableMap.class).set(ComponentBuilderPostConstructListenerImpl.class, ComponentBuilderPostConstructListenerFunctionRunnableImpl.class,LEVEL);
		__inject__(FunctionRunnableMap.class).set(ComponentBuilderExecuteListenerBeforeImpl.class, ComponentBuilderExecuteListenerBeforeFunctionRunnableImpl.class,LEVEL);
		__inject__(FunctionRunnableMap.class).set(ComponentBuilderExecuteListenerAfterImpl.class, ComponentBuilderExecuteListenerAfterFunctionRunnableImpl.class,LEVEL);
		__inject__(FunctionRunnableMap.class).set(MessageRenderImpl.class, MessageRenderFunctionRunnableImpl.class,LEVEL);
		__inject__(FunctionRunnableMap.class).set(StyleClassBuilderWidthImpl.class, StyleClassBuilderWidthCssPrimefacesGridFunctionRunnableImpl.class,LEVEL);
		__inject__(FunctionRunnableMap.class).set(LayoutWidthGetterImpl.class, LayoutWidthGetterFunctionRunnableImpl.class,LEVEL);
		__inject__(FunctionRunnableMap.class).set(ComponentRoleStyleClassGetterImpl.class, ComponentRoleStyleClassGetterFunctionRunnableImpl.class,LEVEL);
		__inject__(FunctionRunnableMap.class).set(ComponentTargetModelBuilderImpl.class, ComponentTargetModelBuilderFunctionRunnableImpl.class,LEVEL);
		__inject__(FunctionRunnableMap.class).set(NavigationIdentifierToUrlStringMapperImpl.class, NavigationIdentifierToUrlStringMapperFunctionRunnableImpl.class,LEVEL);
		//__inject__(FunctionRunnableMap.class).set(RequestPropertyValueGetterImpl.class, RequestPropertyValueGetterFunctionRunnableImpl.class,LEVEL);
		__inject__(FunctionRunnableMap.class).set(RequestParameterValueGetterImpl.class, RequestParameterValueGetterFunctionRunnableImpl.class,LEVEL);
		__inject__(FunctionRunnableMap.class).set(GridBuilderCommandableBuilderProcessorImpl.class, GridBuilderCommandableBuilderProcessorFunctionRunnableImpl.class,LEVEL);
		//__inject__(FunctionRunnableMap.class).set(CommandFunctionImpl.class, CommandFunctionFunctionRunnableImpl.class,LEVEL);
		__inject__(FunctionRunnableMap.class).set(NavigationRedirectorImpl.class, NavigationRedirectorFunctionRunnableImpl.class,LEVEL);
		
		__inject__(org.cyk.utility.client.controller.web.jsf.ApplicationScopeLifeCycleListener.class).initialize(null);
		
		__setQualifierClassTo__(Primefaces.class, CommandFunction.class);
	}
	
	@Override
	public void __destroy__(Object object) {}
	
	/**/
	
	public static final Integer LEVEL = new Integer(org.cyk.utility.client.controller.web.jsf.ApplicationScopeLifeCycleListener.LEVEL+1);
}
