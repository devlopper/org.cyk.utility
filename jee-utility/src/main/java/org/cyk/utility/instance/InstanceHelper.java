package org.cyk.utility.instance;

import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.helper.Helper;
import org.cyk.utility.number.NumberHelper;

public interface InstanceHelper extends Helper {

	InstanceHelper get();
	ClassHelper getClassHelper();
	NumberHelper getNumberHelper();
	
}
