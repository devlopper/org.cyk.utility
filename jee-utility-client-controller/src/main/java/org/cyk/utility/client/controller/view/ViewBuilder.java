package org.cyk.utility.client.controller.view;

import org.cyk.utility.client.controller.component.VisibleComponentBuilders;
import org.cyk.utility.client.controller.component.VisibleComponentsBuilder;
import org.cyk.utility.client.controller.component.command.Commandables;
import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface ViewBuilder extends FunctionWithPropertiesAsInput<View> {

	VisibleComponentsBuilder getVisibleComponentsBuilder();
	VisibleComponentsBuilder getVisibleComponentsBuilder(Boolean injectIfNull);
	ViewBuilder setVisibleComponentsBuilder(VisibleComponentsBuilder visibleComponentsBuilder);
	
	OutputStringTextBuilder getNameOutputStringTextBuilder();
	OutputStringTextBuilder getNameOutputStringTextBuilder(Boolean injectIfNull);
	ViewBuilder setNameOutputStringTextBuilder(OutputStringTextBuilder nameOutputStringTextBuilder);
	
	VisibleComponentBuilders getComponentBuilders();
	VisibleComponentBuilders getComponentBuilders(Boolean injectIfNull);
	ViewBuilder setComponentBuilders(VisibleComponentBuilders componentBuilders);
	
	ViewBuilder addInputBuilder(InputBuilder<?,?> inputBuilder);
	
	Commandables getProcessingCommandables();
	Commandables getProcessingCommandables(Boolean injectIfNull);
	ViewBuilder setProcessingCommandables(Commandables processingCommandables);
	
}
