package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

@Singleton
public class DateHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	private static DateHelper INSTANCE;
	
	public static DateHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new DateHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
}
