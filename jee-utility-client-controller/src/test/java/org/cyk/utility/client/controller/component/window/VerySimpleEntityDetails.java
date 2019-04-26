package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.client.controller.data.DataIdentifiedByStringAndCoded;

public interface VerySimpleEntityDetails extends DataIdentifiedByStringAndCoded {

	@Override VerySimpleEntityDetails setIdentifier(Object identifier);
	@Override VerySimpleEntityDetails setCode(String code);
	
	String getAddress();
	VerySimpleEntityDetails setAddress(String address);
	
	/**/
	
	public static final String PROPERTY_ADDRESS = "address";
	
}
