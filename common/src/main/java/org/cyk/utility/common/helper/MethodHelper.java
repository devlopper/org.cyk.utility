package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

@Singleton
public class MethodHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static MethodHelper INSTANCE;
	
	public static MethodHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new MethodHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public String getMethodNameAt(Integer index){
		//System.out.println(commonUtils.convertToString(Thread.currentThread().getStackTrace(), "\n\r"));
		return Thread.currentThread().getStackTrace()[index].getMethodName();
	}
	
	public String getCurrentMethodName(){
		return getMethodNameAt(3);
	}
	
}
