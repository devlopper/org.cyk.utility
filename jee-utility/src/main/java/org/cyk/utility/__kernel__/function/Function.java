package org.cyk.utility.__kernel__.function;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.__kernel__.properties.Properties;

public interface Function extends Objectable {

	Function setProperties(Properties properties);
	Function execute();
	
}
