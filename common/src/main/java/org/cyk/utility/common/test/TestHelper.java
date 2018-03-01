package org.cyk.utility.common.test;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.helper.AbstractHelper;

@Singleton
public class TestHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	private static TestHelper INSTANCE;
	
	public static TestHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new TestHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	/**/
	
}
