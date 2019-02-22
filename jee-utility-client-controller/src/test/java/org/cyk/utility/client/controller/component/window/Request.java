package org.cyk.utility.client.controller.component.window;

import java.util.Map;

public class Request {

	private Map<String,String> parameters;
	
	public String getParameter(String name) {
		return parameters == null ? null : parameters.get(name);
	}
	
}
