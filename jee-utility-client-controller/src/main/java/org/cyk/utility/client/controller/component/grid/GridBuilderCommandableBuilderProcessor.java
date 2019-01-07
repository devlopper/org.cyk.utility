package org.cyk.utility.client.controller.component.grid;

import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

public interface GridBuilderCommandableBuilderProcessor extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	GridBuilder getGridBuilder();
	GridBuilderCommandableBuilderProcessor setGridBuilder(GridBuilder gridBuilder);
	
	CommandableBuilder getCommandableBuilder();
	GridBuilderCommandableBuilderProcessor setCommandableBuilder(CommandableBuilder commandableBuilder);
	
}
