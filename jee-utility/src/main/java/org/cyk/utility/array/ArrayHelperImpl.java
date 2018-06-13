package org.cyk.utility.array;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.helper.AbstractHelper;

@Singleton
public class ArrayHelperImpl extends AbstractHelper implements ArrayHelper,Serializable  {
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean isEmpty(Object[] objects) {
		return objects == null ? Boolean.TRUE : objects.length == 0;
	}
	
	@Override
	public Boolean isNotEmpty(Object[] objects) {
		return Boolean.FALSE.equals(isEmpty(objects));
	}
}
