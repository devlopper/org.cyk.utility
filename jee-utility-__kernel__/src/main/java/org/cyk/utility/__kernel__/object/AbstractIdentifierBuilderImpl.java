package org.cyk.utility.__kernel__.object;

import java.io.Serializable;

import org.cyk.utility.__kernel__.log.LogHelper;

public abstract class AbstractIdentifierBuilderImpl extends AbstractObject implements IdentifierBuilder,Serializable {

	@Override
	public Object build(Object object, Type type) {
		if(object == null)
			return null;
		if(type == null)
			type = Type.RUNTIME;
		return __build__(object, type);
	}

	protected Object __build__(Object object, Type type) {
		Object identifier = null;
		if(Type.RUNTIME.equals(type))
			identifier = __buildRuntime__(object);
		if(identifier == null)
			LogHelper.logWarning(String.format("%s identifier of %s(%s) is null", type.name(),object.getClass().getName(),object.toString()), getClass());
		return identifier;
	}
	
	protected Object __buildRuntime__(Object object) {
		return object.toString();
	}
}
