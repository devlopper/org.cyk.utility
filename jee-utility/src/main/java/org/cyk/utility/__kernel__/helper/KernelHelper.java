package org.cyk.utility.__kernel__.helper;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface KernelHelper extends Objectable {

	String getInterfaceImplementationNameSuffix();
	
	String getInterfaceSimpleName(Class<?> aClass);

}
