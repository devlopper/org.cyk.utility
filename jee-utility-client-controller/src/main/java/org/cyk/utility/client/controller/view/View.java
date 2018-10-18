package org.cyk.utility.client.controller.view;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.VisibleComponents;
import org.cyk.utility.client.controller.component.command.Commandables;
import org.cyk.utility.client.controller.component.output.OutputStringText;

public interface View extends VisibleComponent {

	VisibleComponents getVisibleComponents();
	View setVisibleComponents(VisibleComponents visibleComponents);
	
	/* Visible components filter */
	
	/* The name of the view */
	
	OutputStringText getName();
	OutputStringText getName(Boolean injectIfNull);
	View setName(OutputStringText name);
	
	/* The commands to process the view*/
	
	Commandables getProcessingCommandables();
	View setProcessingCommandables(Commandables processingCommandables);
	
}
