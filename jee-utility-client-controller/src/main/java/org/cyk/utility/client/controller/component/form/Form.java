package org.cyk.utility.client.controller.component.form;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.view.View;

public interface Form extends VisibleComponent {

	View getView();
	Form setView(View view);
	
}
