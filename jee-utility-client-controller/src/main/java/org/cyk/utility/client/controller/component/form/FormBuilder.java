package org.cyk.utility.client.controller.component.form;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.view.ViewBuilder;

public interface FormBuilder extends VisibleComponentBuilder<Form> {

	ViewBuilder getView();
	ViewBuilder getView(Boolean injectIfNull);
	FormBuilder setView(ViewBuilder view);
	
}
