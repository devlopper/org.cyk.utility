package org.cyk.utility.client.controller.component.column;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.output.OutputStringText;

public interface Column extends VisibleComponent {

	OutputStringText getHeader();
	Column setHeader(OutputStringText header);
	
}
