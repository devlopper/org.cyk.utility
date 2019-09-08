package org.cyk.utility.client.controller.session;

import java.io.Serializable;

import org.cyk.utility.helper.AbstractHelper;

public abstract class AbstractSessionHelperImpl extends AbstractHelper implements SessionHelper,Serializable {
	private static final long serialVersionUID = 1L;

	private static SessionHelper INSTANCE;
	public static SessionHelper getInstance(Boolean isNew) {
		if(INSTANCE == null || Boolean.TRUE.equals(isNew))
			INSTANCE = __inject__(SessionHelper.class);
		return INSTANCE;
	}
	public static SessionHelper getInstance() {
		return getInstance(null);
	}
	
	@Override
	public String getAttributeNameAsString(Object name) {
		return __getAttributeNameAsString__(name);
	}
	
	public static String __getAttributeNameAsString__(Object name) {
		return name == null ? null : name.toString();
	}
}
