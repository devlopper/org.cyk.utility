package org.cyk.utility.__kernel__.function;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;

public abstract class AbstractFunctionImpl extends AbstractObject implements Function,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Function setProperties(Properties properties) {
		return (Function) super.setProperties(properties);
	}

	@Override
	public final Function execute() {
		__execute__();
		return null;
	}
	
	protected abstract void __execute__();
	
}
