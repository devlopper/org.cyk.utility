package org.cyk.utility.system;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.helper.AbstractHelper;

@Singleton
public class OperatingSystemHelperImpl extends AbstractHelper implements OperatingSystemHelper, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getProperty(String name) {
		return System.getenv(name);
	}
	
}
