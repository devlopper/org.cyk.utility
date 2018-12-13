package org.cyk.utility.client.controller.window;

import org.cyk.utility.client.controller.data.Data;

public interface VerySimpleEntityDetails extends Data {

	@Override VerySimpleEntityDetails setIdentifier(Object identifier);
	@Override VerySimpleEntityDetails setCode(String code);
	
	String getAddress();
	VerySimpleEntityDetails setAddress(String address);
	
	/**/
	
	public static final String PROPERTY_ADDRESS = "address";
	
}
