package org.cyk.utility.system;

import org.cyk.utility.helper.Helper;

public interface SystemHelper extends Helper {

	String getProperty(String name);
	String getProperty(String name,Boolean defaultOnOperatingSystemIfBlank);
}
