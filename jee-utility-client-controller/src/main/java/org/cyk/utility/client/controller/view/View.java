package org.cyk.utility.client.controller.view;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.layout.Layout;
import org.cyk.utility.client.controller.component.output.OutputStringText;

public interface View extends VisibleComponent {

	Layout getLayout();
	View setLayout(Layout layout);
	
	OutputStringText getName();
	OutputStringText getName(Boolean injectIfNull);
	View setName(OutputStringText name);
	
}
