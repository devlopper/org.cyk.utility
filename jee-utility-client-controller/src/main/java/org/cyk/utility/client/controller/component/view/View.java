package org.cyk.utility.client.controller.component.view;

import org.cyk.utility.client.controller.component.Components;
import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.command.CommandableByClassMap;
import org.cyk.utility.client.controller.component.command.Commandables;
import org.cyk.utility.client.controller.component.output.OutputStringText;

public interface View extends VisibleComponent {

	Components getComponents();
	View setComponents(Components components);
	
	/* Visible components filter */
	
	/* The name of the view */
	
	OutputStringText getName();
	OutputStringText getName(Boolean injectIfNull);
	View setName(OutputStringText name);
	
	/* The commands to process the view*/
	
	Commandables getProcessingCommandables();
	View setProcessingCommandables(Commandables processingCommandables);
	
	/**/
	
	View setInputOutputValueFromFieldValue();
	View setFieldValueFromValue(Class<?>...classes);
	
	/*
	 * Commandables map of the view
	 * */
	
	CommandableByClassMap getCommandableByClassMap();
	View setCommandableByClassMap(CommandableByClassMap commandableByClassMap);
	
}
