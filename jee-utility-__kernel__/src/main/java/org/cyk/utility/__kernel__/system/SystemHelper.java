package org.cyk.utility.__kernel__.system;

import org.cyk.utility.__kernel__.string.StringHelper;

public interface SystemHelper {

	static String getProperty(String name,String defaultToIfBlank) {
		if(StringHelper.isBlank(name))
			return null;
		String value = System.getProperty(name);
		if(StringHelper.isBlank(value))
			value = defaultToIfBlank;
		return value;
	}
	
	static String getProperty(String name) {
		return getProperty(name, null);
	}
}
