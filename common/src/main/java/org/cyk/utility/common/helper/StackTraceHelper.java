package org.cyk.utility.common.helper;

import java.io.Serializable;

public class StackTraceHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	private static StackTraceHelper INSTANCE;
	
	public static StackTraceHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new StackTraceHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public StackTraceElement getAt(Integer index){
		return Thread.currentThread().getStackTrace()[index];
	}
	
	public StackTraceElement getCurrent(){
		return getAt(3);
	}
	
}
