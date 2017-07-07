package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.Constant;

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
	
	public String getNamesFromStackTrace(String separator){
		return commonUtils.convertToString(Thread.currentThread().getStackTrace(), separator);
	}
	
	public String getNamesStackTrace(){
		return getNamesFromStackTrace(Constant.LINE_DELIMITER);
	}
	
	public String getNameFromStackTraceAt(Integer index){
		return Thread.currentThread().getStackTrace()[index].getMethodName();
	}
	
	public String getCurrentNameFromStackTrace(){
		return getNameFromStackTraceAt(3);
	}
	
}
