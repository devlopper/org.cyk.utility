package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.component.ComponentBuilderExecuteListenerAfterImpl;
import org.cyk.utility.client.controller.component.ComponentBuilderExecuteListenerBeforeImpl;
import org.cyk.utility.client.controller.component.ComponentBuilderPostConstructListenerImpl;
import org.cyk.utility.client.controller.component.ComponentPostConstructListenerImpl;
import org.cyk.utility.client.controller.component.ComponentRoleStyleClassGetterImpl;
import org.cyk.utility.client.controller.component.ComponentTargetModelBuilderImpl;
import org.cyk.utility.client.controller.component.command.CommandFunctionImpl;
import org.cyk.utility.client.controller.component.grid.GridBuilderCommandableBuilderProcessorImpl;
import org.cyk.utility.client.controller.component.layout.LayoutWidthGetterImpl;
import org.cyk.utility.client.controller.component.layout.StyleClassBuilderWidthCssPrimefacesGridFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.theme.ThemeClassGetterImpl;
import org.cyk.utility.client.controller.message.MessageRenderImpl;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierToUrlStringMapperImpl;
import org.cyk.utility.css.StyleClassBuilderWidthImpl;
import org.cyk.utility.request.RequestParameterValueGetterImpl;
import org.cyk.utility.request.RequestPropertyValueGetterImpl;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		__inject__(FunctionRunnableMap.class).set(ComponentPostConstructListenerImpl.class, ComponentPostConstructListenerFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(ComponentBuilderPostConstructListenerImpl.class, ComponentBuilderPostConstructListenerFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(ComponentBuilderExecuteListenerBeforeImpl.class, ComponentBuilderExecuteListenerBeforeFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(ComponentBuilderExecuteListenerAfterImpl.class, ComponentBuilderExecuteListenerAfterFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(MessageRenderImpl.class, MessageRenderFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(StyleClassBuilderWidthImpl.class, StyleClassBuilderWidthCssPrimefacesGridFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(LayoutWidthGetterImpl.class, LayoutWidthGetterFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(ThemeClassGetterImpl.class, ThemeClassGetterFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(ComponentRoleStyleClassGetterImpl.class, ComponentRoleStyleClassGetterFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(ComponentTargetModelBuilderImpl.class, ComponentTargetModelBuilderFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(NavigationIdentifierToUrlStringMapperImpl.class, NavigationIdentifierToUrlStringMapperFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(RequestPropertyValueGetterImpl.class, RequestPropertyValueGetterFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(RequestParameterValueGetterImpl.class, RequestParameterValueGetterFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(GridBuilderCommandableBuilderProcessorImpl.class, GridBuilderCommandableBuilderProcessorFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(CommandFunctionImpl.class, CommandFunctionFunctionRunnableImpl.class,5);
	}
	
	@Override
	public void __destroy__(Object object) {}
	
}
