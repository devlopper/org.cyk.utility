package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

@Singleton
public class RegularExpressionHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static RegularExpressionHelper INSTANCE;
	
	public static RegularExpressionHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new RegularExpressionHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
}
