package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

public class WindowRenderTypeNormalImpl extends AbstractWindowRenderTypeImpl implements WindowRenderTypeNormal,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenAfterPostConstruct__() {
		super.__listenAfterPostConstruct__();
		setIdentifier(IDENTIFIER);
	}
	
}
