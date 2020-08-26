package org.cyk.utility.server.representation;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface DataLoader {

	Response load();
	
	/**/
	
	static DataLoader getInstance() {
		return Helper.getInstance(DataLoader.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}
