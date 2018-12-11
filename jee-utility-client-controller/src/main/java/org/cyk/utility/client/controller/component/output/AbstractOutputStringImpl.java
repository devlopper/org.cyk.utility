package org.cyk.utility.client.controller.component.output;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.string.StringConstant;

public abstract class AbstractOutputStringImpl extends AbstractOutputImpl<String> implements OutputString,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object ____getValueToSetValueFromFieldValue____(Object object, Field field) {
		Object result =  super.____getValueToSetValueFromFieldValue____(object, field);
		if(result == null)
			result = StringConstant.EMPTY;
		else if(result instanceof String)
			;
		else
			result = result.toString();
		return result;
	}
	
}
