package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

import org.apache.commons.lang3.ArrayUtils;

@Singleton
public class ArrayHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static ArrayHelper INSTANCE;
	
	public static ArrayHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new ArrayHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public Object[] reverse(Object[] objects){
		ArrayUtils.reverse(objects);
		return objects;
	}
}
