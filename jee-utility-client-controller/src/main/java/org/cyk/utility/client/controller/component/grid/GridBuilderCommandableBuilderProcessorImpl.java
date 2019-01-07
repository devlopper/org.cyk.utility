package org.cyk.utility.client.controller.component.grid;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;

public class GridBuilderCommandableBuilderProcessorImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements GridBuilderCommandableBuilderProcessor,Serializable {
	private static final long serialVersionUID = 1L;

	private GridBuilder gridBuilder;
	private CommandableBuilder commandableBuilder;
	
	@Override
	public GridBuilder getGridBuilder() {
		return gridBuilder;
	}
	
	@Override
	public GridBuilderCommandableBuilderProcessor setGridBuilder(GridBuilder gridBuilder) {
		this.gridBuilder = gridBuilder;
		return this;
	}
	
	@Override
	public CommandableBuilder getCommandableBuilder() {
		return commandableBuilder;
	}
	
	@Override
	public GridBuilderCommandableBuilderProcessor setCommandableBuilder(CommandableBuilder commandableBuilder) {
		this.commandableBuilder = commandableBuilder;
		return this;
	}
	
}
