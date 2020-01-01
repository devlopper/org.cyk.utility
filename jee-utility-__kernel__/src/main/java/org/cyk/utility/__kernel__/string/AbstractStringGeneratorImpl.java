package org.cyk.utility.__kernel__.string;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.object.AbstractObject;

public abstract class AbstractStringGeneratorImpl extends AbstractObject implements StringGenerator,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String generate(Object template, Map<String, Object> arguments) {
		if(template == null)
			return null;
		return __generate__(template, arguments);
	}
	
	protected abstract String __generate__(Object template, Map<String, Object> arguments);
}
