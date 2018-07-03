package org.cyk.utility.value;

import java.io.Serializable;

import org.cyk.utility.helper.AbstractHelper;

public class ValueHelperImpl extends AbstractHelper implements ValueHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public <T> T defaultToIfNull(Class<T> aClass,T value,T defaultValue){
		return value == null ? defaultValue : value;
	}
	
	@Override
	public <T> T defaultToIfNull(T value,T defaultValue){
		return value == null ? defaultValue : value;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <FROM, CLASS> CLASS cast(Object object, CLASS aClass) {
		return (CLASS) object;
	}
}
