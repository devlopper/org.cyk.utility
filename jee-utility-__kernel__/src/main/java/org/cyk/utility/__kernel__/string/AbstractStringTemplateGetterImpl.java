package org.cyk.utility.__kernel__.string;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.AbstractObject;

public abstract class AbstractStringTemplateGetterImpl extends AbstractObject implements StringTemplateGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Object get(String identifier) {
		if(StringHelper.isBlank(identifier))
			return null;
		return __get__(identifier);
	}
	
	protected abstract Object __get__(String identifier);
	
}
