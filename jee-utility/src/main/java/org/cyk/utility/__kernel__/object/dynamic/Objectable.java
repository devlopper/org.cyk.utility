package org.cyk.utility.__kernel__.object.dynamic;

import org.cyk.utility.__kernel__.properties.Properties;

public interface Objectable extends org.cyk.utility.__kernel__.object.Objectable {

	Properties getProperties();
	Objectable setProperties(Properties properties);
	
	Object getParent();
	Objectable setParent(Object parent);
	
	Object getIdentifier();
	Objectable setIdentifier(Object identifier);
}
