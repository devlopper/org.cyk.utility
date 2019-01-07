package org.cyk.utility.client.controller.component.form;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;

public class FormCommandableBuilderProcessorImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements FormCommandableBuilderProcessor,Serializable {
	private static final long serialVersionUID = 1L;

	private Form form;
	private CommandableBuilder commandableBuilder;
	
	@Override
	public Form getForm() {
		return form;
	}
	
	@Override
	public FormCommandableBuilderProcessor setGridBuilder(Form form) {
		this.form = form;
		return this;
	}
	
	@Override
	public CommandableBuilder getCommandableBuilder() {
		return commandableBuilder;
	}
	
	@Override
	public FormCommandableBuilderProcessor setCommandableBuilder(CommandableBuilder commandableBuilder) {
		this.commandableBuilder = commandableBuilder;
		return this;
	}
	
}
