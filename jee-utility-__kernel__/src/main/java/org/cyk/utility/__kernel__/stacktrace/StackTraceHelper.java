package org.cyk.utility.__kernel__.stacktrace;

import org.cyk.utility.__kernel__.object.dynamic.Singleton;

public interface StackTraceHelper extends Singleton {

	StackTraceElement getAt(Integer index);

	StackTraceElement getCurrent();

	String getStackTraceAsString(String packagePrefix);
	
	String getStackTraceAsString();
	
	String getCallerMethodName(Integer offset);
	
	String getCallerMethodName();
}
