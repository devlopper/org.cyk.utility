package org.cyk.utility.stream.distributed;

import java.io.Serializable;

import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;

public abstract class AbstractConsumerMessageProcessorImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements ConsumerMessageProcessor,Serializable {
	private static final long serialVersionUID = 1L;

	private Message message;
	
	@Override
	protected void ____execute____() throws Exception {
		Message message = ValueHelper.returnOrThrowIfBlank("consumed message", getMessage());
		__process__(message);
	}
	
	protected abstract void __process__(Message message);
	
	@Override
	public Message getMessage() {
		return message;
	}
	
	@Override
	public ConsumerMessageProcessor setMessage(Message message) {
		this.message = message;
		return this;
	}
}
