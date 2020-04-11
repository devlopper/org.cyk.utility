package org.cyk.utility.__kernel__.user.interface_;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface UserInterfaceEventListener {

	void listen(UserInterfaceEvent event);
	
	/**/
	
	static UserInterfaceEventListener getInstance() {
		return Helper.getInstance(UserInterfaceEventListener.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}