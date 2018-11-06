package org.cyk.utility.string;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.object.ObjectByIntegerMap;

public interface StringFormat extends Objectable {

	String getValue();
	StringFormat setValue(String format);

	ObjectByIntegerMap getArgumentMap();
	StringFormat setArgumentMap(ObjectByIntegerMap argumentMap);
	StringFormat setArguments(Object...arguments);
	
	String evaluate();
}
