package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

@Singleton
public class ValueHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static ValueHelper INSTANCE;
	
	public static ValueHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new ValueHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	
	/**/
	
	public static interface Generator<T> {
		
		
		
	}
}
