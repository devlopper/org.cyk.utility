package org.cyk.utility.client.controller.data;

import org.cyk.utility.helper.Helper;
import org.cyk.utility.system.action.SystemAction;

public interface DataHelper extends Helper {

	Class<Row> getRowClass(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Class<Row> getRowClass(SystemAction systemAction);
	
	Row injectRow(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Row injectRow(SystemAction systemAction);
	
	Class<Form> getFormClass(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Class<Form> getFormClass(SystemAction systemAction);
	
	Form injectForm(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Form injectForm(SystemAction systemAction);
}
