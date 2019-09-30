package org.cyk.utility.client.controller.data;

import org.cyk.utility.field.Fields;
import org.cyk.utility.helper.Helper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public interface DataHelper extends Helper {

	Class<Data> getDataClass(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Class<Data> getDataClass(SystemAction systemAction);
	
	Data injectData(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Data injectData(SystemAction systemAction);
	
	Class<Row> getRowClass(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Class<Row> getRowClass(SystemAction systemAction);
	
	Row injectRow(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Row injectRow(SystemAction systemAction);
	
	Class<Form> getFormClass(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Class<Form> getFormClass(SystemAction systemAction);
	
	Form injectForm(Class<?> entityClass,Class<? extends SystemAction> systemActionClass);
	Form injectForm(SystemAction systemAction);
	
	Fields getPropertiesFields(Class<?> anInterface);
	Strings getPropertiesFieldsNames(Class<?> anInterface);
}
