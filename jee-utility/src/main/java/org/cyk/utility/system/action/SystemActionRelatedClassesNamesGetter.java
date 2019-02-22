package org.cyk.utility.system.action;

import org.cyk.utility.string.StringsFunction;
import org.cyk.utility.system.action.SystemAction;

public interface SystemActionRelatedClassesNamesGetter extends StringsFunction {

	Class<? extends SystemAction> getSystemActionClass();
	SystemActionRelatedClassesNamesGetter setSystemActionClass(Class<? extends SystemAction> systemActionClass);

	Class<?> getEntityClass();
	SystemActionRelatedClassesNamesGetter setEntityClass(Class<?> entityClass);
	
	Class<?> getExtendedInterface();
	SystemActionRelatedClassesNamesGetter setExtendedInterface(Class<?> extendedInterface);
	
	String getNameSuffix();
	SystemActionRelatedClassesNamesGetter setNameSuffix(String nameSuffix);
	
	String getDefaultSuffix();
	SystemActionRelatedClassesNamesGetter setDefaultSuffix(String defaultSuffix);
}
