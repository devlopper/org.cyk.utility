package org.cyk.utility.client.controller.data;

import org.cyk.utility.client.controller.Objectable;

public interface Data extends Objectable {
	
	String getCode();
	Data setCode(String code);
	
	public static final String PROPERTY_CODE = "code";
}
