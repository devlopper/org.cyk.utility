package org.cyk.utility.client.controller.session;

import org.cyk.utility.helper.Helper;

@Deprecated
public interface SessionHelper extends Helper {

	String getAttributeNameAsString(Object name);
	
	Object getAttributeValue(Object name,Object request);
	SessionHelper setAttributeValue(Object name,Object value,Object request);
	
}
