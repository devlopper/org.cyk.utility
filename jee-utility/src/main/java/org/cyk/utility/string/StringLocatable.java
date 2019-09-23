package org.cyk.utility.string;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.__kernel__.string.StringLocation;

public interface StringLocatable extends Objectable {

	String getString();
	StringLocatable setString(String string);
	
	StringLocation getLocation();
	StringLocatable setLocation(StringLocation location);
}
