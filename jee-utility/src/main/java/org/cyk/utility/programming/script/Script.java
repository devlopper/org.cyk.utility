package org.cyk.utility.programming.script;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface Script extends Objectable {

	String getCodeSource();
	Script setCodeSource(String codeSource);
	
}
