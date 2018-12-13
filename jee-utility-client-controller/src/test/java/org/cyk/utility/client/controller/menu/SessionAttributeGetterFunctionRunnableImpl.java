package org.cyk.utility.client.controller.menu;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.session.SessionAttributeEnumeration;
import org.cyk.utility.client.controller.session.SessionAttributeGetter;

public class SessionAttributeGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<SessionAttributeGetter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static Menu MENU_SESSION_01;
	
	public SessionAttributeGetterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				if(SessionAttributeEnumeration.MENU.equals(getFunction().getAttribute())) {
					setOutput(MENU_SESSION_01);
				}
			}
		});
	}
	
}