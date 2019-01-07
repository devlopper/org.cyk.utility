package org.cyk.utility.client.controller.component.form;

import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

public interface FormCommandableBuilderProcessor extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	Form getForm();
	FormCommandableBuilderProcessor setGridBuilder(Form form);
	
	CommandableBuilder getCommandableBuilder();
	FormCommandableBuilderProcessor setCommandableBuilder(CommandableBuilder commandableBuilder);
	
}
