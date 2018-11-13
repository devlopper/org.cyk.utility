package org.cyk.utility.client.controller.data;

import org.cyk.utility.client.controller.Objectable;
import org.cyk.utility.system.action.SystemAction;

public interface Row extends Objectable {

	String getUrlBySystemActionClass(Class<? extends SystemAction> aClass);
	
}
