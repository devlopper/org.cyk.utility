package org.cyk.utility.system.action;

import org.cyk.utility.clazz.ClassFunction;
import org.cyk.utility.string.Strings;

public interface SystemActionRelatedClassGetter extends ClassFunction {

	SystemActionRelatedClassesNamesGetter getNamesGetter();
	SystemActionRelatedClassGetter setNamesGetter(SystemActionRelatedClassesNamesGetter namesGetter);

	Strings getNames();
	SystemActionRelatedClassGetter setNames(Strings names);
}
