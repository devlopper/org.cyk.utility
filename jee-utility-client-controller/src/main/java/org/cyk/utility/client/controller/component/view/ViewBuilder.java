package org.cyk.utility.client.controller.component.view;

import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.ComponentsBuilder;
import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilders;
import org.cyk.utility.client.controller.component.command.CommandableButtonBuilder;
import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineManyBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineOneBuilder;
import org.cyk.utility.system.action.SystemAction;

public interface ViewBuilder extends VisibleComponentBuilder<View> {

	ViewType getType();
	ViewBuilder setType(ViewType type);
	
	ComponentsBuilder getComponentsBuilder();
	ComponentsBuilder getComponentsBuilder(Boolean injectIfNull);
	ViewBuilder setComponentsBuilder(ComponentsBuilder componentsBuilder);
	
	//<T extends InputOutputBuilder<?,?>> T addInputOutputBuilder(Class<T> inputOutputBuilderClass);
	
	<T extends InputBuilder<?,?>> T addInputBuilder(Class<T> inputBuilderClass,Object outputPropertyRequired,Object labelBuilderOutputPropertyValue);
	InputStringLineOneBuilder addInputStringLineOneBuilder(Object outputPropertyRequired,Object labelBuilderOutputPropertyValue);
	InputStringLineManyBuilder addInputStringLineManyBuilder(Object outputPropertyRequired,Object labelBuilderOutputPropertyValue);
	ViewBuilder addInputBuilder(InputBuilder<?,?> inputBuilder);
	
	<T extends InputBuilder<?,?>> T addInputBuilderByFieldName(Class<T> inputBuilderClass,Object object,String...fieldNames);
	InputBuilder<?,?> addInputBuilderByFieldName(Object object,String...fieldNames);
	
	ViewBuilder addComponentBuilder(ComponentBuilder<?> componentBuilder);
	<T extends ComponentBuilder<?>> T addComponentBuilderByObjectByFieldNames(Class<T> componentBuilderClass,Object object,String...fieldNames);
	ComponentBuilder<?> addComponentBuilderByObjectByFieldNames(Object object,String...fieldNames);
	<T extends ComponentBuilder<?>> T addComponentBuilderByObjectByMethodName(Class<T> componentBuilderClass,Object object,String methodName);
	ComponentBuilder<?> addComponentBuilderByObjectByMethodName(Object object,String methodName);
	
	CommandableBuilders getProcessingCommandableBuilders();
	CommandableBuilders getProcessingCommandableBuilders(Boolean injectIfNull);
	ViewBuilder setProcessingCommandableBuilders(CommandableBuilders processingCommandableBuilders);
	
	<T extends CommandableBuilder<?>> T addProcessingCommandableBuilder(Class<T> aClass,Object commandableOutputPropertyValue,Class<? extends SystemAction> systemActionClass,Runnable...runnables);
	CommandableButtonBuilder addProcessingCommandableButtonBuilder(Object commandableOutputPropertyValue,Class<? extends SystemAction> systemActionClass,Runnable...runnables);
	
	CommandableBuilder<?> getSubmitCommandableBuilder();
	CommandableBuilder<?> getSubmitCommandableBuilder(Boolean injectIfNull);
	ViewBuilder setSubmitCommandableBuilder(CommandableBuilder<?> submitCommandableBuilder);
	
	CommandableBuilder<?> getCloseCommandableBuilder();
	ViewBuilder setCloseCommandableBuilder(CommandableBuilder<?> closeCommandableBuilder);
}
