package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

import org.apache.commons.lang3.ArrayUtils;

@Singleton
public class ThrowableHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static ThrowableHelper INSTANCE;
	
	public static ThrowableHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new ThrowableHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public Throwable getInstanceOf(Throwable throwable,Class<?> aClass){
		Throwable index = throwable;
		while(index!=null){
			if(aClass.isAssignableFrom(index.getClass())){
				return index;
			}else
				index = index.getCause();
		}
		return null;
	}
	
	public Throwable getInstanceOf(Throwable throwable,Class<?> aClass,Class<?>...classes){
		for(Class<?> index : ArrayUtils.add(classes, aClass)){
			Throwable instance = getInstanceOf(throwable, index);
			if(instance!=null)
				return instance;
		}
		return null;
	}
	
	public Throwable getFirstCause(Throwable throwable){
		Throwable cause=throwable,index = throwable;
		while(index!=null){
			cause = index;
			index = index.getCause();
		}
		return cause;
	}
		
	
	
}
