package org.cyk.utility.common.helper;

import java.io.Serializable;

public class ThreadHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static ThreadHelper INSTANCE;
	
	public static ThreadHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new ThreadHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	
}
