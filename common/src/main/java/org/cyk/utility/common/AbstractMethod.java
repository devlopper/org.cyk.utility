package org.cyk.utility.common;

import java.io.Serializable;

public abstract class AbstractMethod<RESULT,PARAMETER> implements Serializable {

	private static final long serialVersionUID = 1010323978651662605L;

	public final RESULT execute(PARAMETER parameter){
		return __execute__(parameter);
	}
	
	public final RESULT execute(){
		return __execute__(null);
	}

	protected abstract RESULT __execute__(PARAMETER parameter);
	
	protected void exceptionParameter(String message){
		throw new RuntimeException("Parameters structure is incorrect : "+message);
	}
}
