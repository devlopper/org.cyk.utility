package org.cyk.utility.client.controller.view;

import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.VisibleComponentBuilders;
import org.cyk.utility.client.controller.component.VisibleComponentsBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilders;
import org.cyk.utility.client.controller.component.command.CommandableButtonBuilder;
import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineManyBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineOneBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.system.action.SystemAction;

public interface ViewBuilder extends FunctionWithPropertiesAsInput<View> {

	ViewType getType();
	ViewBuilder setType(ViewType type);
	
	VisibleComponentsBuilder getVisibleComponentsBuilder();
	VisibleComponentsBuilder getVisibleComponentsBuilder(Boolean injectIfNull);
	ViewBuilder setVisibleComponentsBuilder(VisibleComponentsBuilder visibleComponentsBuilder);
	
	OutputStringTextBuilder getNameOutputStringTextBuilder();
	OutputStringTextBuilder getNameOutputStringTextBuilder(Boolean injectIfNull);
	ViewBuilder setNameOutputStringTextBuilder(OutputStringTextBuilder nameOutputStringTextBuilder);
	ViewBuilder setNameOutputPropertyValue(Object value);
	
	VisibleComponentBuilders getComponentBuilders();
	VisibleComponentBuilders getComponentBuilders(Boolean injectIfNull);
	ViewBuilder setComponentBuilders(VisibleComponentBuilders componentBuilders);
	
	//<T extends InputOutputBuilder<?,?>> T addInputOutputBuilder(Class<T> inputOutputBuilderClass);
	
	<T extends InputBuilder<?,?>> T addInputBuilder(Class<T> inputBuilderClass,Object outputPropertyRequired,Object labelBuilderOutputPropertyValue);
	InputStringLineOneBuilder addInputStringLineOneBuilder(Object outputPropertyRequired,Object labelBuilderOutputPropertyValue);
	InputStringLineManyBuilder addInputStringLineManyBuilder(Object outputPropertyRequired,Object labelBuilderOutputPropertyValue);
	ViewBuilder addInputBuilder(InputBuilder<?,?> inputBuilder);
	
	<T extends InputBuilder<?,?>> T addInputBuilderByFieldName(Class<T> inputBuilderClass,Object object,String...fieldNames);
	InputBuilder<?,?> addInputBuilderByFieldName(Object object,String...fieldNames);
	
	ViewBuilder addComponentBuilder(VisibleComponentBuilder<?> componentBuilder);
	<T extends ComponentBuilder<?>> T addComponentBuilderByFieldName(Class<T> componentBuilderClass,Object object,String...fieldNames);
	ComponentBuilder<?> addComponentBuilderByFieldName(Object object,String...fieldNames);
	
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
