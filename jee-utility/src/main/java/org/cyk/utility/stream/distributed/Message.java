package org.cyk.utility.stream.distributed;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface Message extends Objectable {

	Object getKey();
	Message setKey(Object key);
	
	Object getValue();
	Message setValue(Object value);
}
