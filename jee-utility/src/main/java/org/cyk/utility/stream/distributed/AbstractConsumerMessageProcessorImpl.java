package org.cyk.utility.stream.distributed;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;

public abstract class AbstractConsumerMessageProcessorImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements ConsumerMessageProcessor,Serializable {
	private static final long serialVersionUID = 1L;

	private Object message;
	
	@Override
	protected void ____execute____() throws Exception {
		Object message = __injectValueHelper__().returnOrThrowIfBlank("message", getMessage());
		__process__(message);
	}
	
	protected abstract void __process__(Object message);
	
	@Override
	public Object getMessage() {
		return message;
	}
	
	@Override
	public ConsumerMessageProcessor setMessage(Object message) {
		this.message = message;
		return this;
	}
}
