package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

import org.apache.commons.lang3.RandomStringUtils;

@Singleton
public class RandomHelper extends AbstractHelper implements Serializable {
	static final long serialVersionUID = 1L;
	
	private static RandomHelper INSTANCE;
	
	public static RandomHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new RandomHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public <T> T get(Class<T> aClass){
		if(java.lang.String.class.equals(aClass))
			return (T) RandomStringUtils.randomAlphabetic(5);
		ThrowableHelper.getInstance().throwNotYetImplemented();
		return null;
	}
	
}
