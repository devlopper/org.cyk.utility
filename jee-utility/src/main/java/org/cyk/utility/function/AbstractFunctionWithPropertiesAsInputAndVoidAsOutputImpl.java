package org.cyk.utility.function;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.throwable.ThrowableHelper;

public abstract class AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl extends AbstractFunctionImpl<Properties, Void> implements FunctionWithPropertiesAsInputAndVoidAsOutput,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Void __execute__() throws Exception {
		____execute____();
		return null;
	}
	
	protected void ____execute____() throws Exception {
		throw ThrowableHelper.IMPLEMENTATION_OR_RUNNABLE_REQUIRED;
	}
	
}
