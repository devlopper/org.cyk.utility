package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

public class WindowRenderTypeDialogImpl extends AbstractWindowRenderTypeImpl implements WindowRenderTypeDialog,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenAfterPostConstruct__() {
		super.__listenAfterPostConstruct__();
		setIdentifier(IDENTIFIER);
	}
	
}
