package org.cyk.utility.function;

import java.io.Serializable;

public abstract class AbstractFunctionWithVoidAsOutputImpl<INPUT> extends AbstractFunctionImpl<INPUT, Void> implements FunctionWithVoidAsOutput<INPUT>,Serializable {
	private static final long serialVersionUID = 1L;

}
