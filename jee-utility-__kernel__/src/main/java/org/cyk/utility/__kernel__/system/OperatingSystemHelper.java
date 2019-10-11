package org.cyk.utility.__kernel__.system;

public interface OperatingSystemHelper {

	static String getProperty(String name) {
		return System.getenv(name);
	}
	
}
