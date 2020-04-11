package org.cyk.utility.__kernel__.controller;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface ViewEventListener {

	void listen(ViewEvent event);
	
	/**/
	
	static ViewEventListener getInstance() {
		return Helper.getInstance(ViewEventListener.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}