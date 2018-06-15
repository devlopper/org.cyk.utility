package org.cyk.utility.function;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;

public abstract class AbstractFunctionWithPropertiesAsInputImpl<OUTPUT> extends AbstractFunctionImpl<Properties, OUTPUT> implements FunctionWithPropertiesAsInput<OUTPUT>,Serializable {
	private static final long serialVersionUID = 1L;

}
