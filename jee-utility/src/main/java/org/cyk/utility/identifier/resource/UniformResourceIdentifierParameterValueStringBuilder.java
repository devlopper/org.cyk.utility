package org.cyk.utility.identifier.resource;

import org.cyk.utility.string.StringFunction;

public interface UniformResourceIdentifierParameterValueStringBuilder extends StringFunction {

	Object getValue();
	UniformResourceIdentifierParameterValueStringBuilder setValue(Object value);
	
}
