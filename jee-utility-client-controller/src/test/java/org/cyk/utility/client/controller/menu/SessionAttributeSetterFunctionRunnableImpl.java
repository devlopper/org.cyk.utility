package org.cyk.utility.client.controller.menu;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.session.SessionAttributeEnumeration;
import org.cyk.utility.client.controller.session.SessionAttributeSetter;

public class SessionAttributeSetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<SessionAttributeSetter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public SessionAttributeSetterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				if(SessionAttributeEnumeration.MENU.equals(getFunction().getAttribute())) {
					SessionAttributeGetterFunctionRunnableImpl.MENU_SESSION_01 = (Menu) getFunction().getValue();
				}
			}
		});
	}
	
}