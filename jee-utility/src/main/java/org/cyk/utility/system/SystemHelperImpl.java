package org.cyk.utility.system;

import java.io.Serializable;

import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.string.StringHelper;

public class SystemHelperImpl extends AbstractHelper implements SystemHelper, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getProperty(String name) {
		return System.getProperty(name);
	}

	@Override
	public String getProperty(String name, Boolean defaultOnOperatingSystemIfBlank) {
		String value = getProperty(name);
		if(__inject__(StringHelper.class).isBlank(value) && Boolean.TRUE.equals(defaultOnOperatingSystemIfBlank)) {
			value = __inject__(OperatingSystemHelper.class).getProperty(name);
		}
		return value;
	}
	
}
